package org.moonzhou.springbootmysqlfulltext.repository;

import org.moonzhou.springbootmysqlfulltext.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT a.* FROM Article a WHERE MATCH(a.title, a.content) AGAINST(:searchKeyword IN BOOLEAN MODE)", nativeQuery = true)
    List<Article> searchByContent(@Param("searchKeyword") String searchKeyword);
}