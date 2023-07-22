package com.whathappened.whathappendbackend.service;

import com.whathappened.whathappendbackend.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ArticleService {
    ArticleRepository articleRepository;


}
