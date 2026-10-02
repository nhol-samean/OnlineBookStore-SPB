package com.example.project_bsdb.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T>{
    private Boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    public static <T>ApiResponse<T> success(String msg,T data){
        return ApiResponse.<T>builder()
                .success(true)
                .message(msg)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T>ApiResponse<T> error(String message){
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
