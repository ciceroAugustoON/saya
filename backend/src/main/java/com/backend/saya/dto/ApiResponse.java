package com.backend.saya.dto;

public class ApiResponse {
    private String error;
    private String message;
    private Object data;

    public static ApiResponse success(Object data) {
        return new ApiResponse(null, null, data);
    }

    public static ApiResponse error(String error, String message) {
        return new ApiResponse(error, message, null);
    }

    private ApiResponse(String error, String message, Object data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}