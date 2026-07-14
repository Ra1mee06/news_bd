package app.controller;

import app.dto.CommentRequest;
import app.dto.CommentResponse;
import app.entity.User;
import app.security.SecurityUtils;
import app.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final SecurityUtils securityUtils;

    public CommentController(CommentService commentService, SecurityUtils securityUtils) {
        this.commentService = commentService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public List<CommentResponse> findAll() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentResponse findById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public CommentResponse create(@Valid @RequestBody CommentRequest request) {
        User currentUser = securityUtils.getCurrentUser();
        return commentService.create(request, currentUser);
    }

    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable Long id, @Valid @RequestBody CommentRequest request) {
        User currentUser = securityUtils.getCurrentUser();
        return commentService.update(id, request, currentUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User currentUser = securityUtils.getCurrentUser();
        commentService.delete(id, currentUser);
    }
}
