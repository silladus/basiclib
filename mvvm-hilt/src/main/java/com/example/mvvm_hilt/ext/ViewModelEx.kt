package com.example.mvvm_hilt.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

/**
 * create by silladus 2020/7/24
 * github:https://github.com/silladus
 * des:
 */
inline fun <T> ViewModel.launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {
    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emitSource(block())
    }
}