package com.backend.saya.dto;

import com.backend.saya.entities.enumeration.Archetype;

public class UserResponse {
    private Long userId;
    private String displayName;
    private boolean objectivesDefined;

    private String token;

    public UserResponse() {
    }

    public UserResponse(Long userId, String displayName, boolean objectivesDefined, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.objectivesDefined = objectivesDefined;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isObjectivesDefined() {
        return objectivesDefined;
    }

    public void setObjectivesDefined(boolean objectivesDefined) {
        this.objectivesDefined = objectivesDefined;
    }
}
