package com.example.mvvm_hilt.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by silladus on 2020/6/21.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provide(): String {
        Timber.e(hashCode().toString())
        return hashCode().toString()
    }

//    @Singleton
//    @Provides
//    fun provideGithubService(): GithubService {
//        return Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
//            .build()
//            .create(GithubService::class.java)
//    }

//    @Singleton
//    @Provides
//    fun provideDb(app: Application): RecordDb {
////        return Room
////            .databaseBuilder(app, RecordDb::class.java, "record.db")
////            .fallbackToDestructiveMigration()
////            .build()
//        return RecordDb.getDb()
//    }
//
//    @Singleton
//    @Provides
//    fun provideUserDao(db: RecordDb): AccountInfoDao {
//        return db.accountInfoDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRecordDao(db: RecordDb): RecordDao {
//        return db.recordDao()
//    }
}