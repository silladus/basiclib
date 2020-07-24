package com.example.mvvm_hilt.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_hilt.IAct
import com.example.mvvm_hilt.di.ActivityScope
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IAct {
//    @AppScope
    @Inject
    lateinit var appHash: String

    @ActivityScope
    @Inject
    lateinit var activityHash: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, MainFragment(), MainFragment::class.java.simpleName)
                .commit()

        Timber.e("$appHash, $activityHash")

//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
    }
}
