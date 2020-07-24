package com.example.mvvm_hilt.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.mvvm_hilt.entity.PokemonResponse
import com.example.mvvm_hilt.entity.Result
import com.example.mvvm_hilt.ext.launchOnViewModelScope
import com.example.mvvm_hilt.repository.MainRepository

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
class MainViewModel @ViewModelInject constructor(private val repository: MainRepository /*@Assisted private val savedState: SavedStateHandle*/) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val liveData: LiveData<Result<PokemonResponse>> /*by lazy { MutableLiveData<com.example.mvvm_hilt.entity.Result<PokemonResponse>>() }*/

    private val reqLiveData = MutableLiveData<Int>()

    init {
        liveData = reqLiveData.switchMap {
            isLoading.value = true
            launchOnViewModelScope {
                repository.getData(
                        page = it,
                        onSuccess = {
                            isLoading.postValue(false)
                        }
                ).asLiveData()
            }
        }
    }

    fun getData(page: Int) {
        reqLiveData.value = page
    }

}