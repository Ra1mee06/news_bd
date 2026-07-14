package app.controller;

import app.dto.NewsDetailsResponse;
import app.dto.NewsRequest;
import app.dto.NewsResponse;
import app.entity.User;
import app.security.SecurityUtils;
import app.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final SecurityUtils securityUtils;

    public NewsController(NewsService newsService, SecurityUtils securityUtils) {
        this.newsService = newsService;
        this.securityUtils = securityUtils;
    }

    /**
     * Список новостей с пагинацией (доступен без авторизации).
     */
    @GetMapping
    public Page<NewsResponse> findAll(Pageable pageable) {
        return newsService.findAll(pageable);
    }

    /**
     * Просмотр новости с комментариями (доступен без авторизации).
     */
    @GetMapping("/{id}")
    public NewsDetailsResponse findById(@PathVariable Long id) {
        return newsService.findByIdWithComments(id);
    }

    @PostMapping
    public NewsResponse create(@Valid @RequestBody NewsRequest request) {
        User currentUser = securityUtils.getCurrentUser();
        return newsService.create(request, currentUser);
    }

    @PutMapping("/{id}")
    public NewsResponse update(@PathVariable Long id, @Valid @RequestBody NewsRequest request) {
        User currentUser = securityUtils.getCurrentUser();
        return newsService.update(id, request, currentUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User currentUser = securityUtils.getCurrentUser();
        newsService.delete(id, currentUser);
    }
}
