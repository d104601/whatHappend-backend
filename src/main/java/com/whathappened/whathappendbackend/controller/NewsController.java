package com.whathappened.whathappendbackend.controller;

import com.whathappened.whathappendbackend.service.NewsService;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam String mkt,
            @RequestParam String location,
            @RequestParam String language
    ) {
        return newsService.getSearch(q, mkt, location, language);
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
