package com.example.mvvm_hilt.ext

import kotlinx.coroutines.Deferred

/**
 * create by silladus 2020/7/22
 * github:https://github.com/silladus
 * des:
 */
suspend fun <T> Deferred<T>.awaitOrError(): Result<T> {
    return try {
        Result.of(await())
    } catch (e: Exception) {
        Result.of(e)
    }
}

//suspend fun <T> Deferred<Response<T>>.awaitOrError(): Result<T> {
//    return try {
//        val ret = await()
//        Result.of(ret.body())
//    } catch (e: Exception) {
//        Result.of(e)
//    }
//}