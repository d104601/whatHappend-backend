package com.whathappened.whathappendbackend.service;

import com.whathappened.whathappendbackend.repository.NewsRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Getter
@Setter
public class NewsService {
    @Value("${api.bing.key}")
    private String apiKey;
    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public String getAllTrends(String mkt) {
        String url = "https://api.bing.microsoft.com/v7.0/news/trendingtopics?mkt=" + mkt;

        RestTemplate restTemplate = new RestTemplate();
        // apply key to header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // get response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // print out response
        return response.getBody();
    }

    public String getSearch(String q, String location, String language, int offset, String freshness) {
        StringBuilder url = new StringBuilder("https://api.bing.microsoft.com/v7.0/news/search?q=" + q + "&sortBy=Relevance&offset=" + offset);
        if (!Objects.equals(location, "")) {
            url.append("&mkt=").append(location);
        }
        if (!Objects.equals(language, "")) {
            url.append("&language=").append(language);
        }
        if (!Objects.equals(freshness, "")) {
            url.append("&freshness=").append(freshness);
        }

        RestTemplate restTemplate = new RestTemplate();
        // apply key to header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // get response
        ResponseEntity<String> response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, String.class);

        // print out response
        return response.getBody();
    }
}
