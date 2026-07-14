package app.repository;

import app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByNews_IdOrderByCreatedAtAsc(Long newsId);
}
