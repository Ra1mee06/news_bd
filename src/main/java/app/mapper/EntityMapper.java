package app.mapper;

import app.dto.CommentResponse;
import app.dto.NewsDetailsResponse;
import app.dto.NewsResponse;
import app.dto.UserResponse;
import app.entity.Comment;
import app.entity.News;
import app.entity.User;

import java.util.List;

public final class EntityMapper {

    private EntityMapper() {
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getParentName(),
                user.getRole(),
                user.getCreatedAt(),
                user.getEditedAt()
        );
    }

    public static NewsResponse toNewsResponse(News news) {
        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getText(),
                news.getCreatedAt(),
                news.getEditedAt(),
                news.getInsertedById(),
                news.getUpdatedById()
        );
    }

    public static CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getNewsId(),
                comment.getCreatedAt(),
                comment.getEditedAt(),
                comment.getInsertedById()
        );
    }

    public static NewsDetailsResponse toNewsDetailsResponse(News news, List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(EntityMapper::toCommentResponse)
                .toList();
        return new NewsDetailsResponse(
                news.getId(),
                news.getTitle(),
                news.getText(),
                news.getCreatedAt(),
                news.getEditedAt(),
                news.getInsertedById(),
                news.getUpdatedById(),
                commentResponses
        );
    }
}
