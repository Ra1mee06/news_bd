package app.dto;

import app.entity.Role;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String parentName;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public UserResponse(Long id, String username, String name, String surname, String parentName,
                        Role role, LocalDateTime createdAt, LocalDateTime editedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.parentName = parentName;
        this.role = role;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getParentName() {
        return parentName;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }
}
