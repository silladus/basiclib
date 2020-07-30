package com.example.mvvm_hilt.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.mvvm_hilt.entity.PokemonInfo
import com.example.mvvm_hilt.entity.Result
import com.example.mvvm_hilt.ext.launchOnViewModelScope
import com.example.mvvm_hilt.repository.DetailRepository

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
class DetailViewModel @ViewModelInject constructor(private val repository: DetailRepository /*@Assisted private val savedState: SavedStateHandle*/) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val liveData: LiveData<Result<PokemonInfo>>

    private val reqLiveData = MutableLiveData<String>()

    init {
        liveData = reqLiveData.switchMap {
            isLoading.value = true
            launchOnViewModelScope {
                repository.getData(
                        name = it,
                        onSuccess = {
                            isLoading.postValue(false)
                        }
                ).asLiveData()
            }
        }
    }

    fun getData(name: String) {
        reqLiveData.value = name
    }

}