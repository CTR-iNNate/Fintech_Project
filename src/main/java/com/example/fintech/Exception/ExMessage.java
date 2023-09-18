package com.example.fintech.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExMessage {

    UNDEFINED_ERROR("정의되지 않은 에러")
    , NOT_FOUND_ERROR("요청 객체 없음")
    , DUPLICATE_ERROR("중복 개체");

    private String message;

}
