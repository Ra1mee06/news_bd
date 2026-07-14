package app.dto;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private String text;
    private Long newsId;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private Long insertedBy;

    public CommentResponse(Long id, String text, Long newsId, LocalDateTime createdAt, LocalDateTime editedAt,
                           Long insertedBy) {
        this.id = id;
        this.text = text;
        this.newsId = newsId;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.insertedBy = insertedBy;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getNewsId() {
        return newsId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public Long getInsertedBy() {
        return insertedBy;
    }
}
