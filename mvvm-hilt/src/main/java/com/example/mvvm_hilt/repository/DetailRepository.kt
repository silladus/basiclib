package com.example.mvvm_hilt.repository

import com.example.mvvm_hilt.db.PokemonInfoDao
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
class DetailRepository @Inject constructor(
        private val pokeDexApi: PokeDexApi,
        private val pokemonInfoDao: PokemonInfoDao
) : IRepository {

    suspend fun getData(
            name: String,
            onSuccess: () -> Unit
    ) = flow {
        val data = pokemonInfoDao.getPokemonInfo(name)
        Timber.e("PokemonInfo from db:$data")
        if (data == null) {
            val response = wrapDataOrError {
                pokeDexApi.fetchPokemonInfo(name).also {
                    pokemonInfoDao.insertPokemonInfo(it)
                }
            }
            emit(response)
            onSuccess()
        } else {
            data.also { emit(wrapDataOrError { it }) }
            onSuccess()
        }
    }.flowOn(Dispatchers.IO)
}