package com.example.mvvm_dagger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
class MainViewModel @Inject constructor(/*private val userRepository: UserRepository*/) : ViewModel() {

    val liveData by lazy { MutableLiveData<String>() }

    fun getData() {
        viewModelScope.launch {
            delay(5000)
            liveData.value = "data got from remote OK"
        }
    }
}