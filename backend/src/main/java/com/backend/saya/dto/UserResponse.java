package com.backend.saya.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private Long userId;
    private String displayName;
    private String token;
    private boolean objectivesDefined;

    public UserResponse() {
    }

    public UserResponse(Long userId, String displayName, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.token = token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setObjectivesDefined(boolean objectivesDefined) {
        this.objectivesDefined = objectivesDefined;
    }
}
