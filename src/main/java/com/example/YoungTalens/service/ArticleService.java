package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.ArticleDTO;
import com.example.YoungTalens.entity.Articles;
import com.example.YoungTalens.mapper.ArticleMapper;
import com.example.YoungTalens.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    public ArticleDTO saveArticle(Articles article) {
        Articles savedArticle = articleRepository.save(article);
        return articleMapper.toDto(savedArticle);
    }

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream().map(articleMapper::toDto).collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        return articleRepository.findById(id).map(articleMapper::toDto).orElse(null);
    }
}
