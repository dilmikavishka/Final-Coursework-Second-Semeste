package lk.ijse.heladivaproject.repo;

import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ArticleDao extends JpaRepository<Article,String> {
        Article findFirstByOrderByArticleIdDesc();
}
