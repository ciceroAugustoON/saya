package com.backend.saya.dto;

import lombok.Getter;

public class ApiResponse {
    @Getter
    private final String error;
    @Getter
    private final String message;
    @Getter
    private final Object data;
    private final boolean success;

    public static ApiResponse success(Object data) {
        return new ApiResponse(null, null, data, true);
    }

    public static ApiResponse error(String error, String message) {
        return new ApiResponse(error, message, null, false);
    }

    private ApiResponse(String error, String message, Object data, boolean success) {
        this.error = error;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public boolean isSuccessful() {
        return success;
    }

}