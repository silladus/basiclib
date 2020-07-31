package com.example.mvvm_hilt.di

import android.app.Application
import androidx.room.Room
import com.example.mvvm_hilt.db.AppDatabase
import com.example.mvvm_hilt.db.PokemonDao
import com.example.mvvm_hilt.db.PokemonInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
                .databaseBuilder(application, AppDatabase::class.java, "Pokedex.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDatabase: AppDatabase): PokemonDao {
        return appDatabase.pokemonDao()
    }

    @Provides
    @Singleton
    fun providePokemonInfoDao(appDatabase: AppDatabase): PokemonInfoDao {
        return appDatabase.pokemonInfoDao()
    }
}
