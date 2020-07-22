package com.example.mvvm_dagger.ext

import com.example.mvvm_dagger.data.entry.Response
import kotlinx.coroutines.Deferred

/**
 * create by silladus 2020/7/22
 * github:https://github.com/silladus
 * des:
 */
//suspend fun <T> Deferred<T>.awaitOrError(): com.example.mvvm_dagger.data.entry.Result<T> {
//    return try {
//        com.example.mvvm_dagger.data.entry.Result.of(await())
//    } catch (e: Exception) {
//        com.example.mvvm_dagger.data.entry.Result.of(e)
//    }
//}

suspend fun <T> Deferred<Response<T>>.awaitOrError(): com.example.mvvm_dagger.data.entry.Result<T> {
    return try {
        val ret = await()
        com.example.mvvm_dagger.data.entry.Result.of(ret.body())
    } catch (e: Exception) {
        com.example.mvvm_dagger.data.entry.Result.of(e)
    }
}