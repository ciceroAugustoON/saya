package com.timeless.saya.feature.auth.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    private String userId;
    private String displayName;

    private boolean objectivesDefined;

    private String token;

    public LoggedInUser(String userId, String displayName, boolean objectivesDefined, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.objectivesDefined = objectivesDefined;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
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