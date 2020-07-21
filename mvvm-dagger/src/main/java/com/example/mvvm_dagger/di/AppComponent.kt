package com.example.mvvm_dagger.di

import android.app.Application
import com.example.mvvm_dagger.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            MainActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}