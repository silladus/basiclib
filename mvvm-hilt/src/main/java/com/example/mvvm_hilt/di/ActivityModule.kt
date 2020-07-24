package com.example.mvvm_hilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by silladus on 2020/6/21.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @ActivityScope
    @Provides
    fun provideString(): String {
        return hashCode().toString()
    }
}