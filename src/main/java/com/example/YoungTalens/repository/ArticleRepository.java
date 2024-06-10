package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Articles, Long> {

}
