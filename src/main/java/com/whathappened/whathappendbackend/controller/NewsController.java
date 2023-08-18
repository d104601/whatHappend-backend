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
}
