package lk.ijse.heladivaproject.controller;

import lk.ijse.heladivaproject.dto.ArticleDTO;
import lk.ijse.heladivaproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "http://localhost:63342",exposedHeaders = "Authorization")
@RequiredArgsConstructor
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    public final ArticleService articleService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/admin/save")
    public ResponseEntity<Map<String, Object>> saveArticle(@RequestBody ArticleDTO articleDTO) {
        log.info("Admin creating an article");
        ResponseEntity<String> serviceResponse = articleService.save(articleDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Article saved successfully!");
        return ResponseEntity.ok(response);
    }



    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/admin/update")
    public ResponseEntity<Map<String,Object>> updateArticle(@RequestBody ArticleDTO articleDTO) {
        log.info("Admin updating article with ID: ");
        ResponseEntity<String> serviceResponse = articleService.update(articleDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Article Updated successfully!");
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable String id) {
        log.info("Admin deleting article with ID: {}", id);
        articleService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Article deleted successfully!");
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable String id) {
        log.info("Fetching article with ID: {}", id);

        ArticleDTO articleDTO = articleService.getArticleById(id);

        return ResponseEntity.ok(articleDTO);
    }

    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/getAll")
    public ResponseEntity<Collection<ArticleDTO>> getAllArticles() {
        log.info("Fetching all articles");
        Collection<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }
}
