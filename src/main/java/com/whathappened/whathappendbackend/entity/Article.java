package com.whathappened.whathappendbackend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("article")
public class Article {
    @Id
    String id;
}
