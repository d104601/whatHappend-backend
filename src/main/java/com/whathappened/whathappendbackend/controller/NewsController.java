package com.whathappened.whathappendbackend.controller;

import com.whathappened.whathappendbackend.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/trend")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getTrend(@RequestParam String mkt) {
        return newsService.getAllTrends(mkt);
    }

    @GetMapping("/search")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getSearch(
            @RequestParam String q,
            @RequestParam Optional<String> location,
            @RequestParam Optional<String> language,
            @RequestParam int offset,
            @RequestParam Optional<String> freshness
    ) {
        // print all params
        System.out.println("q: " + q);
        System.out.println("location: " + location);
        System.out.println("language: " + language);
        System.out.println("offset: " + offset);
        System.out.println("freshness: " + freshness);
        return newsService.getSearch(q, location, language, offset, freshness);
    }

//    @GetMapping("/category")
//    public List<Article> searchNewsByCategory(String category) {
//
//    }
//
//    @GetMapping("/search")
//    public List<Article> searchNewsByKeyword(String keyword) {
//
//    }
}
