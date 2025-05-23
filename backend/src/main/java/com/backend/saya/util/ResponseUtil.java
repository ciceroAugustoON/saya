package com.backend.saya.util;

import com.backend.saya.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity<ApiResponse> generateResponseEntity(ApiResponse response) {
        if (response.isSuccessful()) return ResponseEntity.ok(response);
        HttpStatus status = HttpStatus.valueOf(response.getError());
        return ResponseEntity.status(status).body(ApiResponse.error(status.getReasonPhrase(), response.getMessage()));
    }
}
