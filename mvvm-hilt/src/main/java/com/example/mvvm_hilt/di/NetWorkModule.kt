package com.example.mvvm_hilt.di

import com.example.mvvm_hilt.BuildConfig
import com.example.mvvm_hilt.net.PokeDexApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by silladus on 2020/6/21.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetWorkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
//                    .cookieJar(new JavaNetCookieJar(new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ALL)))
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                })
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(if (BuildConfig.DEBUG) "https://pokeapi.co/api/v2/" else "https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun providePokeDexApi(retrofit: Retrofit): PokeDexApi {
        return retrofit.create(PokeDexApi::class.java)
    }
}