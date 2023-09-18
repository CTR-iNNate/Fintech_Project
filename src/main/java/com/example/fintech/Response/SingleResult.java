package com.example.fintech.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

    public class SingleResult<T> extends CommonResult {
        private T data;

}
