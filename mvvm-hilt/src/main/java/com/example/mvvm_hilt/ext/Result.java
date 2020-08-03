package com.example.mvvm_hilt.ext;

import androidx.annotation.Nullable;

/**
 * create by silladus 2020/7/22
 * github:https://github.com/silladus
 * des:
 */
public class Result<T> {
    public T data;
    public Throwable error;

    public T component1() {
        return data;
    }

    @Nullable
    public Throwable component2() {
        return error;
    }

    public static <T> Result<T> of(T data) {
        Result<T> result = new Result<>();
        result.data = data;
        return result;
    }

    public static <T> Result<T> of(Throwable error) {
        Result<T> result = new Result<>();
        result.error = error;
        return result;
    }
}