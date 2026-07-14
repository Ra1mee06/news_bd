package app.service;

import app.dto.NewsDetailsResponse;
import app.dto.NewsRequest;
import app.dto.NewsResponse;
import app.entity.News;
import app.entity.User;
import app.exception.ResourceNotFoundException;
import app.mapper.EntityMapper;
import app.repository.CommentRepository;
import app.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    public NewsService(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public Page<NewsResponse> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable).map(EntityMapper::toNewsResponse);
    }

    @Transactional(readOnly = true)
    public NewsDetailsResponse findByIdWithComments(Long id) {
        News news = getNews(id);
        return EntityMapper.toNewsDetailsResponse(news, commentRepository.findByNews_IdOrderByCreatedAtAsc(id));
    }

    public NewsResponse create(NewsRequest request, User currentUser) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setText(request.getText());
        news.setInsertedBy(currentUser);
        news.setUpdatedBy(currentUser);
        return EntityMapper.toNewsResponse(newsRepository.save(news));
    }

    public NewsResponse update(Long id, NewsRequest request, User currentUser) {
        News news = getNews(id);
        checkNewsOwnership(news, currentUser);
        news.setTitle(request.getTitle());
        news.setText(request.getText());
        news.setUpdatedBy(currentUser);
        return EntityMapper.toNewsResponse(newsRepository.save(news));
    }

    public void delete(Long id, User currentUser) {
        News news = getNews(id);
        checkNewsOwnership(news, currentUser);
        newsRepository.delete(news);
    }

    private News getNews(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Новость не найдена: " + id));
    }

    /**
     * Журналист может изменять только свои новости, администратор — любые.
     */
    private void checkNewsOwnership(News news, User currentUser) {
        if (UserService.isAdmin(currentUser)) {
            return;
        }
        if (UserService.isReporter(currentUser) && news.getInsertedBy().getId().equals(currentUser.getId())) {
            return;
        }
        throw new AccessDeniedException("Недостаточно прав для изменения новости");
    }
}
