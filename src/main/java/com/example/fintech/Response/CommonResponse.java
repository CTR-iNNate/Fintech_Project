package com.example.fintech.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum CommonResponse {

    SUCCESS(1, "성공"),
    FAIL(-1, "실패");

    private int code;
    private String message;
}
