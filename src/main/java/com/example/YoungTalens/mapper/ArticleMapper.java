package com.example.YoungTalens.mapper;

import com.example.YoungTalens.dto.ArticleDTO;
import com.example.YoungTalens.entity.Articles;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public ArticleDTO toDto(Articles article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getDate(),
                article.getLocation(),
                article.getImage()
        );
    }

    public Articles toEntity(ArticleDTO dto) {
        Articles article = new Articles();
        article.setId(dto.id());
        article.setTitle(dto.title());
        article.setDescription(dto.description());
        article.setDate(dto.date());
        article.setLocation(dto.location());
        article.setImage(dto.image());
        return article;
    }
}
