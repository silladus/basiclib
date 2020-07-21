package com.example.mvvm_dagger.di

import com.example.mvvm_dagger.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

//    @ContributesAndroidInjector
//    abstract fun contributeRepoFragment(): RepoFragment
//
//    @ContributesAndroidInjector
//    abstract fun contributeUserFragment(): UserFragment
//
//    @ContributesAndroidInjector
//    abstract fun contributeSearchFragment(): SearchFragment
}