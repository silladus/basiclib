package com.example.mvvm_hilt.ext

/**
 * create by silladus 2020/8/3
 * github:https://github.com/silladus
 * des:
 */
interface IRepository

inline fun <T> IRepository.wrapDataOrError(d: () -> T): Result<T> {
    return try {
        Result.of(d())
    } catch (e: Exception) {
        Result.of(e)
    }
}