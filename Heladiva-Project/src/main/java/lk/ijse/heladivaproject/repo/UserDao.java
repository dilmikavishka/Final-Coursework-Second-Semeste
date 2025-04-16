package lk.ijse.heladivaproject.repo;

import jakarta.transaction.Transactional;

import lk.ijse.heladivaproject.entity.Article;
import lk.ijse.heladivaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    User findFirstByOrderByUserIdDesc();
}
