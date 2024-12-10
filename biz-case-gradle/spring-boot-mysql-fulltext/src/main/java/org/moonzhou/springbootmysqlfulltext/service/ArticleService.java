package org.moonzhou.springbootmysqlfulltext.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.moonzhou.springbootmysqlfulltext.entity.Article;
import org.moonzhou.springbootmysqlfulltext.repository.ArticleRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    // @PersistenceContext
    // private EntityManager entityManager;

    private final EntityManagerFactory entityManagerFactory;

    private final ThreadPoolTaskExecutor taskExecutor;
    private final ThreadPoolTaskExecutor monitorExecutor;

    private final AtomicInteger completedTasks;

    public ArticleService(ArticleRepository articleRepository,
                          EntityManagerFactory entityManagerFactory,
                          ThreadPoolTaskExecutor taskExecutor,
                          ThreadPoolTaskExecutor monitorExecutor) {
        this.articleRepository = articleRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.taskExecutor = taskExecutor;
        this.monitorExecutor = monitorExecutor;

        this.completedTasks = new AtomicInteger(0);
    }

    public List<Article> searchArticles(String searchKeyword) {
        return articleRepository.searchByContent(searchKeyword);
    }

    // @Transactional
    // public void generateAndSaveArticles(int numberOfArticles) {
    //     List<Article> articles = new ArrayList<>();
    //     for (int i = 0; i < numberOfArticles; i++) {
    //         Article article = new Article();
    //         article.setTitle("Article Title " + (i + 1));
    //         article.setContent("This is the content of article " + (i + 1) + ".");
    //         articles.add(article);
    //
    //         // 为了避免内存溢出，可以定期保存一部分数据
    //         if (articles.size() % 100 == 0) {
    //             articleRepository.saveAll(articles);
    //             articles.clear();
    //         }
    //     }
    //     // 保存剩余的数据
    //     articleRepository.saveAll(articles);
    // }

    public void generateAndSaveArticles(int numberOfArticles) {
        try {
            List<Article> articles = generateArticles(numberOfArticles);
            int batchSize = 10000; // 每个线程处理的数据量
            int numBatches = (int) Math.ceil((double) numberOfArticles / batchSize);

            List<Future<Void>> futures = new ArrayList<>();
            for (int i = 0; i < numBatches; i++) {
                int start = i * batchSize;
                int end = Math.min(start + batchSize, numberOfArticles);
                List<Article> batch = articles.subList(start, end);
                futures.add(taskExecutor.submit(new ArticleBatchSaver(batch)));
            }

            // 等待所有线程完成
            // for (Future<Void> future : futures) {
            //     future.get();
            // }

            // 主线程不阻塞，独立线程输出保存进度
            printProgress(futures, numberOfArticles);
        } catch (Exception e) {
            log.error("init data error: ", e);
            throw new RuntimeException(e);
        }
    }

    private List<Article> generateArticles(int numberOfArticles) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticles; i++) {
            Article article = new Article();
            article.setTitle("Article Title " + (i + 1));
            article.setContent("This is the content of article " + (i + 1) + ".");
            articles.add(article);
        }
        return articles;
    }

    private class ArticleBatchSaver implements Callable<Void> {
        private final List<Article> articles;

        public ArticleBatchSaver(List<Article> articles) {
            this.articles = articles;
        }

        @Override
        public Void call() throws Exception {
            EntityManager em = entityManagerFactory.createEntityManager();
            try {
                em.getTransaction().begin();

                for (Article article : articles) {
                    em.persist(article);
                }

                em.getTransaction().commit();

                // 更新已完成的任务数量
                completedTasks.addAndGet(articles.size());
            } catch (PersistenceException ex) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw ex; // 重新抛出异常以便上层处理
            } finally {
                em.close();
            }
            return null;
        }
    }

    private void printProgress(List<Future<Void>> futures, int totalArticles) {
        // 使用一个单独的线程来定期打印进度
        // ExecutorService progressExecutor = Executors.newSingleThreadExecutor();
        monitorExecutor.submit(() -> {
            while (true) {
                int completed = completedTasks.get();
                int percentage = (int) ((completed * 100.0) / totalArticles);
                System.out.println("Progress: " + percentage + "%");

                // 每秒打印一次进度
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // 检查是否所有任务都已完成
                if (completed == totalArticles && futures.stream().allMatch(Future::isDone)) {
                    System.out.println("All tasks completed.");
                    this.completedTasks.set(0);
                    break;
                }
            }
        });

        // 注意：在实际应用中，你应该在适当的时候关闭这个进度检查线程池
        // 例如，在所有任务完成后，或者在你的应用程序关闭时
    }
}