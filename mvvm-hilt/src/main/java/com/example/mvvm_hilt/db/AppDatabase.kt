package com.example.mvvm_hilt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvm_hilt.entity.Pokemon
import com.example.mvvm_hilt.entity.PokemonInfo

/**
 * create by silladus 2020/7/31
 * github:https://github.com/silladus
 * des:
 */
@Database(entities = [Pokemon::class, PokemonInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonInfoDao(): PokemonInfoDao
}