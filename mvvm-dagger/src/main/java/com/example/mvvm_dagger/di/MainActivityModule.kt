package com.example.mvvm_dagger.di

import com.example.mvvm_dagger.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by silladus on 2020/6/21.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

//    @ContributesAndroidInjector
//    abstract fun contributeVipActivity(): VipActivity

}