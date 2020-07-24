package com.example.mvvm_hilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * Created by silladus on 2020/6/21.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
    @FragmentScope
    @Provides
    fun provideString(): String {
        return hashCode().toString()
    }
}