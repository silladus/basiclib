package com.example.mvvm_dagger

import android.app.Application
import com.example.mvvm_dagger.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import silladus.basic.util.DebugTree
import timber.log.Timber
import javax.inject.Inject

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:纪念即将逝去的Dagger2
 */

lateinit var appContext: App

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        AppInjector.init(this)

        Timber.plant(DebugTree())

    }
}