package com.whathappened.whathappendbackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Article {
    private String datePublished;
    private String description;
    private Image image;
    private String name;
    private List<Provider> provider;
    private String url;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Image {
        private Thumbnail thumbnail;
        private boolean isLicensed;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Thumbnail {
        private String contentUrl;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Provider {
        private String name;
    }

}
