package org.moonzhou.springbootmysqlfulltext.controller;

import org.moonzhou.springbootmysqlfulltext.entity.Article;
import org.moonzhou.springbootmysqlfulltext.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * http://localhost:8080/article/search?keyword=your_search_term
     * http://localhost:8080/article/search?keyword=99999
     * http://localhost:8080/article/search?keyword=This%20is%20the%20content%20of%20article%2099999.
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public List<Article> searchArticles(@RequestParam String keyword) {
        return articleService.searchArticles(keyword);
    }

    /**
     * http://localhost:8080/article/generate-articles?numberOfArticles=100000
     * @param numberOfArticles
     * @return
     */
    @GetMapping("/generate-articles")
    public String generateArticles(@RequestParam int numberOfArticles) {
        articleService.generateAndSaveArticles(numberOfArticles);
        return "Articles generated and saved successfully!";
    }
}