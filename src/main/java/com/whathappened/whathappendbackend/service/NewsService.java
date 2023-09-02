package com.whathappened.whathappendbackend.service;

import com.whathappened.whathappendbackend.domain.Article;
import com.whathappened.whathappendbackend.domain.User;
import com.whathappened.whathappendbackend.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class NewsService {
    @Value("${api.rapidapi.key}")
    private String apiKey;
    private UserRepository userRepository;

    public NewsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getAllTrends(String mkt) {
        String url = "https://bing-news-search1.p.rapidapi.com/news/trendingtopics?safeSearch=Off&textFormat=Raw&mkt" + mkt;

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader("X-BingApis-SDK", "true")
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .defaultHeader("X-RapidAPI-Host", "bing-news-search1.p.rapidapi.com")
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getSearch(
            String q,
            Optional<String> location,
            Optional<String> language,
            int offset,
            Optional<String> freshness) {
        StringBuilder url = new StringBuilder("https://bing-news-search1.p.rapidapi.com/news/search?q=" + q + "&sortBy=Relevance&offset=" + offset);
        location.ifPresent(s -> url.append("&mkt=").append(s));
        language.ifPresent(s -> url.append("&setlang=").append(s));
        freshness.ifPresent(s -> url.append("&freshness=").append(s));

        WebClient webClient = WebClient.builder()
                .baseUrl(url.toString())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader("X-BingApis-SDK", "true")
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .defaultHeader("X-RapidAPI-Host", "bing-news-search1.p.rapidapi.com")
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getNewsByCateogry(Optional<String> category, String mkt) {
        StringBuilder url = new StringBuilder("https://bing-news-search1.p.rapidapi.com/news");
        url.append("?mkt=").append(mkt);
        category.ifPresent(s -> url.append("&category=").append(s));


        WebClient webClient = WebClient.builder()
                .baseUrl(url.toString())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader("X-BingApis-SDK", "true")
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .defaultHeader("X-RapidAPI-Host", "bing-news-search1.p.rapidapi.com")
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public User saveArticle(Article article, String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                if (user.get().getArticles() == null) {
                    user.get().setArticles(new ArrayList<>());
                }
                // check if article already exists
                for (Article a : user.get().getArticles()) {
                    if (a.getUrl().equals(article.getUrl())) {
                        throw new RuntimeException("already saved");
                    }
                }
                user.get().getArticles().add(article);
                userRepository.save(user.get());
                return user.get();
            }
            else {
                throw new RuntimeException("Error: User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Article> getSavedArticle(String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return user.get().getArticles();
            }
            else {
                throw new RuntimeException("Error: User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Article> removeArticle(String username, String url) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                List<Article> articles = user.get().getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    if (articles.get(i).getUrl().equals(url)) {
                        articles.remove(i);
                        userRepository.save(user.get());
                        return articles;
                    }
                }
                throw new RuntimeException("Error: Article not found");
            }
            else {
                throw new RuntimeException("Error: User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
