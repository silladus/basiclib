package com.example.mvvm_hilt.repository

import com.example.mvvm_hilt.entity.Result
import com.example.mvvm_hilt.net.PokeDexApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * create by silladus 2020/7/23
 * github:https://github.com/silladus
 * des:
 */
class DetailRepository @Inject constructor(
        private val pokeDexApi: PokeDexApi
) {

    suspend fun getData(
            name: String,
            onSuccess: () -> Unit
    ) = flow {
        val response = wrapDataOrError { pokeDexApi.fetchPokemonInfo(name) }
        emit(response)
        onSuccess()
    }.flowOn(Dispatchers.IO)

    private inline fun <T> wrapDataOrError(d: () -> T): Result<T> {
        return try {
            Result.of(d())
        } catch (e: Exception) {
            Result.of(e)
        }
    }
}