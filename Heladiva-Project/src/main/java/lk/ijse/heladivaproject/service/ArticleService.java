package lk.ijse.heladivaproject.service;

import lk.ijse.heladivaproject.dto.ArticleDTO;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ArticleService {
    ResponseEntity<String> save(ArticleDTO articleDTO);

    ResponseEntity<String> update(ArticleDTO articleDTO);

    void delete(String id);

    ArticleDTO getArticleById(String id);

    Collection<ArticleDTO> getAllArticles();
}
