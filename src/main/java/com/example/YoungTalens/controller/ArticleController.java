package com.example.YoungTalens.controller;

import com.example.YoungTalens.dto.ArticleDTO;
import com.example.YoungTalens.mapper.ArticleMapper;
import com.example.YoungTalens.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @PostMapping
    public ArticleDTO createArticle(@RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("date") String date,
                                    @RequestParam("location") String location,
                                    @RequestPart("image") MultipartFile imageFile) throws IOException {
        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imagePath = saveFile(imageFile);
        }

        ArticleDTO articleDTO = new ArticleDTO(
                null,
                title,
                description,
                date,
                location,
                imagePath
        );

        return articleService.saveArticle(articleMapper.toEntity(articleDTO));
    }

    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public ArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadsDir = "uploads/";
        String absoluteUploadsDir = System.getProperty("user.dir") + "/" + uploadsDir;
        File uploadsDirFile = new File(absoluteUploadsDir);

        if (!uploadsDirFile.exists()) {
            uploadsDirFile.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadsDir + fileName;
        File dest = new File(absoluteUploadsDir + fileName);
        file.transferTo(dest);
        return filePath;
    }
}
