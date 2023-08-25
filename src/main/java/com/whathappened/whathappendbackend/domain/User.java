package com.whathappened.whathappendbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @JsonIgnore
    private String id;

    private String username;
    private String password;
    private String email;
    private String role;

    List<Article> articles;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.articles = new ArrayList<>();
    }
}
