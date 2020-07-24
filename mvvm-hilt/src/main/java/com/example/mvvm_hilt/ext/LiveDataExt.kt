package com.example.mvvm_hilt.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by silladus on 2020/6/14.
 * GitHub: https://github.com/silladus
 * Description:
 */

suspend fun <T> LiveData<T>.observe(owner: LifecycleOwner): T = suspendCoroutine { cont ->
    this.observe(owner, Observer {
        cont.resume(it)
    })
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, block: (T) -> Unit) {
    observe(owner, Observer { block(it) })
}