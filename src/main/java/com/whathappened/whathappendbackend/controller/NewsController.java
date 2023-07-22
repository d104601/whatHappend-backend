package com.whathappened.whathappendbackend.controller;

import com.whathappened.whathappendbackend.entity.Article;
import com.whathappened.whathappendbackend.entity.Trend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/news")
public class NewsController {
    @GetMapping("/trend")
    public List<Trend> loadTrends() {

    }

    @GetMapping("/category")
    public List<Article> searchNewsByCategory(String category) {

    }

    @GetMapping("/search")
    public List<Article> searchNewsByKeyword(String keyword) {

    }
}
