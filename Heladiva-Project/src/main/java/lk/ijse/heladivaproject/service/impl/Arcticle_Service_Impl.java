package lk.ijse.heladivaproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.dto.ArticleDTO;
import lk.ijse.heladivaproject.entity.Article;
import lk.ijse.heladivaproject.entity.User;
import lk.ijse.heladivaproject.repo.ArticleDao;
import lk.ijse.heladivaproject.repo.UserDao;
import lk.ijse.heladivaproject.service.ArticleService;
import lk.ijse.heladivaproject.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class Arcticle_Service_Impl implements ArticleService {
    private final Mapping mapping;
    private final ArticleDao arcticleDao;
    private final UserDao userDao;


    private String generateNextArticleId() {
        Article lastArticle = arcticleDao.findFirstByOrderByArticleIdDesc();
        if (lastArticle == null) {
            return "ART-001";
        }
        String lastArticleId = lastArticle.getArticleId();
        int lastId = Integer.parseInt(lastArticleId.split("-")[1]);
        int nextId = lastId + 1;
        return "ART-" + String.format("%03d", nextId);
    }

    @Override
    public ResponseEntity<String> save(ArticleDTO articleDTO) {
        Optional<User> optionalUser = userDao.findById(articleDTO.getAuthorId());
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("Student not found with id: " + optionalUser);
        }
        Article article = mapping.toArticle(articleDTO);
        article.setAuthor(optionalUser.get());
        article.setArticleId(generateNextArticleId());
        arcticleDao.save(article);
        return ResponseEntity.ok("Article saved successfully!");
    }

    @Override
    public ResponseEntity<String> update(ArticleDTO articleDTO) {
        System.out.println();
        Optional<Article> optionalArticle = arcticleDao.findById(String.valueOf(articleDTO.getArticleId()));
        if (!optionalArticle.isPresent()) {
            throw new EntityNotFoundException("Article not found with id: " + articleDTO.getArticleId());
        }

        Article article = optionalArticle.get();
        article.setTitle(articleDTO.getTitle());
        article.setScientificName(articleDTO.getScientificName());
        article.setSeasonality(articleDTO.getSeasonality());
        article.setLocation(articleDTO.getLocation());
        article.setUses(articleDTO.getUses());
        article.setDescription(articleDTO.getDescription());
        article.setHealthBenefits(articleDTO.getHealthBenefits());
        article.setSpringCoordinates(articleDTO.getSpringCoordinates());
        article.setSummerCoordinates(articleDTO.getSummerCoordinates());
        article.setAutumnCoordinates(articleDTO.getAutumnCoordinates());
        article.setWinterCoordinates(articleDTO.getWinterCoordinates());
        article.setPublishedDate(articleDTO.getPublishedDate());
        article.setTags(articleDTO.getTags());
        article.setImageUrl(articleDTO.getImageUrl());
        Optional<User> optionalUser = userDao.findById(articleDTO.getAuthorId());
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + articleDTO.getAuthorId());
        }
        article.setAuthor(optionalUser.get());
        arcticleDao.save(article);

        return ResponseEntity.ok("Article updated successfully!");
    }


    @Override
    public void delete(String id) {
        String articleId;
        try {
            articleId =id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid article ID: " + id);
        }

        if (!arcticleDao.existsById(articleId)) {
            throw new EntityNotFoundException("Article not found with id: " + id);
        }

        arcticleDao.deleteById(articleId);
    }

    public ArticleDTO getArticleById(String id) {
        Optional<Article> optionalArticle = arcticleDao.findById(id);
        if (!optionalArticle.isPresent()) {
            throw new EntityNotFoundException("Article not found with id: " + id);
        }
        return mapping.toArticleDTO(optionalArticle.get());
    }

    public Collection<ArticleDTO> getAllArticles() {
        List<Article> allArticles = arcticleDao.findAll();
        return mapping.toArticleDTOS(allArticles);
    }

}
