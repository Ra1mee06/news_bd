package app.service;

import app.dto.CommentRequest;
import app.dto.CommentResponse;
import app.entity.Comment;
import app.entity.News;
import app.entity.User;
import app.exception.ResourceNotFoundException;
import app.mapper.EntityMapper;
import app.repository.CommentRepository;
import app.repository.NewsRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAll() {
        return commentRepository.findAll().stream()
                .map(EntityMapper::toCommentResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommentResponse findById(Long id) {
        return EntityMapper.toCommentResponse(getComment(id));
    }

    public CommentResponse create(CommentRequest request, User currentUser) {
        News news = newsRepository.findById(request.getNewsId())
                .orElseThrow(() -> new ResourceNotFoundException("Новость не найдена: " + request.getNewsId()));
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setNews(news);
        comment.setInsertedBy(currentUser);
        return EntityMapper.toCommentResponse(commentRepository.save(comment));
    }

    public CommentResponse update(Long id, CommentRequest request, User currentUser) {
        Comment comment = getComment(id);
        checkCommentOwnership(comment, currentUser);
        if (!comment.getNews().getId().equals(request.getNewsId())) {
            News news = newsRepository.findById(request.getNewsId())
                    .orElseThrow(() -> new ResourceNotFoundException("Новость не найдена: " + request.getNewsId()));
            comment.setNews(news);
        }
        comment.setText(request.getText());
        return EntityMapper.toCommentResponse(commentRepository.save(comment));
    }

    public void delete(Long id, User currentUser) {
        Comment comment = getComment(id);
        checkCommentOwnership(comment, currentUser);
        commentRepository.delete(comment);
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Комментарий не найден: " + id));
    }

    /**
     * Подписчик может изменять только свои комментарии, администратор — любые.
     */
    private void checkCommentOwnership(Comment comment, User currentUser) {
        if (UserService.isAdmin(currentUser)) {
            return;
        }
        if (UserService.isSubscriber(currentUser) && comment.getInsertedBy().getId().equals(currentUser.getId())) {
            return;
        }
        throw new AccessDeniedException("Недостаточно прав для изменения комментария");
    }
}
