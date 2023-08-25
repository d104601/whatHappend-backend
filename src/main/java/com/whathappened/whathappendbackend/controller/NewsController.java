package com.whathappened.whathappendbackend.controller;

import com.whathappened.whathappendbackend.domain.Article;
import com.whathappened.whathappendbackend.security.JwtTokenProvider;
import com.whathappened.whathappendbackend.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    Logger logger;
    JwtTokenProvider jwtTokenProvider;
    private final NewsService newsService;

    public NewsController(NewsService newsService, JwtTokenProvider jwtTokenProvider) {
        this.logger = LoggerFactory.getLogger(NewsController.class);
        this.newsService = newsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/trend")
    public String getTrend(@RequestParam String mkt) {
        return newsService.getAllTrends(mkt);
    }

    @GetMapping("/search")
    public String getSearch(
            @RequestParam String q,
            @RequestParam Optional<String> location,
            @RequestParam Optional<String> language,
            @RequestParam int offset,
            @RequestParam Optional<String> freshness
    ) {
        return newsService.getSearch(q, location, language, offset, freshness);
    }

    @GetMapping("/category")
    public String searchNewsByCategory(
            @RequestParam Optional<String> category,
            @RequestParam String mkt
    ) {
        return newsService.getNewsByCateogry(category, mkt);
    }

    @PutMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveArticle(@RequestBody Article article, @RequestHeader("Authorization") String tokenValue) {
        logger.info("method: saveArticle called");
        logger.info("received data: article: " + article.toString());
        logger.info("received token: " + tokenValue);

        try {
            String token = tokenValue.split(" ")[1];
            String username = jwtTokenProvider.getUsernameFromToken(token);
            newsService.saveArticle(article, username);
            return ResponseEntity.ok("Article saved successfully");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/saved")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSavedArticle(@RequestHeader("Authorization") String tokenValue) {
        logger.info("method: getSavedArticle called");
        logger.info("received token: " + tokenValue);

        try {
            String token = tokenValue.split(" ")[1];
            String username = jwtTokenProvider.getUsernameFromToken(token);
            List<Article> articles = newsService.getSavedArticle(username);
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteArticle(@RequestBody Article article, @RequestHeader("Authorization") String tokenValue) {
        logger.info("method: deleteArticle called");
        logger.info("received data: article: " + article.toString());
        logger.info("received token: " + tokenValue);

        try {
            String token = tokenValue.split(" ")[1];
            String username = jwtTokenProvider.getUsernameFromToken(token);
            List<Article> newArticles = newsService.removeArticle(username, article.getUrl());
            // return articles after deletion
            return ResponseEntity.ok(newArticles);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
