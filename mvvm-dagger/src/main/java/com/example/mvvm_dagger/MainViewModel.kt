package com.example.mvvm_dagger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_dagger.data.entry.Response
import com.example.mvvm_dagger.ext.awaitOrError
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
class MainViewModel @Inject constructor(/*private val userRepository: UserRepository*/) : ViewModel() {

    val liveData by lazy { MutableLiveData<com.example.mvvm_dagger.data.entry.Result<String>>() }

//    val liveData by lazy { MutableLiveData<String>() }

//    fun getData() {
//        viewModelScope.launch {
//
//            delay(5000)
//
//            val (ret, err) = async(SupervisorJob()) {
//                val data = Response<String>()
//                data.result = "data got from the remote is OK"
//                data
//            }.awaitOrError()
//
//            if (err != null) {
//                Timber.e(err.message!!)
//                return@launch
//            }
//
//            liveData.value = ret.body()
//        }
//    }

    fun getData() {
        viewModelScope.launch {

            delay(5000)

            liveData.value = async(SupervisorJob()) {
                val data = Response<String>()
                data.result = "data got from the remote is OK"
                data
            }.awaitOrError()
        }
    }
}