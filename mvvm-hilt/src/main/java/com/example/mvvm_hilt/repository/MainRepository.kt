package com.example.mvvm_hilt.repository

import com.example.mvvm_hilt.db.PokemonDao
import com.example.mvvm_hilt.ext.IRepository
import com.example.mvvm_hilt.ext.wrapDataOrError
import com.example.mvvm_hilt.net.PokeDexApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * create by silladus 2020/7/23
 * github:https://github.com/silladus
 * des:
 */
class MainRepository @Inject constructor(
        private val pokeDexApi: PokeDexApi,
        private val pokemonDao: PokemonDao
) : IRepository {

    suspend fun getData(
            page: Int,
            onSuccess: () -> Unit
    ) = flow {
        val data = pokemonDao.getPokemonList(page)
        Timber.e("Pokemon from db:$data")
        if (data.isEmpty()) {
            val response = wrapDataOrError {
                pokeDexApi.fetchPokemonList(20, page * 20).results.also {
                    pokemonDao.insertPokemonList(it)
                }
            }
            emit(response)
            onSuccess()
        } else {
            emit(wrapDataOrError { data })
            onSuccess()
        }
    }.flowOn(Dispatchers.IO)

}