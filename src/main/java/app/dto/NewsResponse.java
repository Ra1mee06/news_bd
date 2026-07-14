package app.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NewsResponse {

    private Long id;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private Long insertedBy;
    private Long updatedBy;

    public NewsResponse(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime editedAt,
                        Long insertedBy, Long updatedBy) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.insertedBy = insertedBy;
        this.updatedBy = updatedBy;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }
}
