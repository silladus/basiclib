package com.example.mvvm_hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import silladus.basic.util.DebugTree
import timber.log.Timber

/**
 * create by silladus 2020/7/23
 * github:https://github.com/silladus
 * des:
 */

lateinit var appContext: App

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        CommonActivityConfig.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}