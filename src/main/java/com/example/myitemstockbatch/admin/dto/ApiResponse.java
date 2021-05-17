package com.example.myitemstockbatch.admin.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ApiResponse implements Serializable {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
