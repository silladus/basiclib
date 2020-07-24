package com.example.mvvm_hilt.repository

import com.example.mvvm_hilt.entity.PokemonResponse
import com.example.mvvm_hilt.entity.Result
import com.example.mvvm_hilt.net.PokedexService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * create by silladus 2020/7/23
 * github:https://github.com/silladus
 * des:
 */
class MainRepository @Inject constructor(
        private val pokedexService: PokedexService
) {

    suspend fun getData(
            page: Int,
            onSuccess: () -> Unit
    ) = flow {
        val response = wrapDataOrError { pokedexService.fetchPokemonList(20, page * 20) }
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