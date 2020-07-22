package com.example.mvvm_dagger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ComplexColorCompat
import silladus.basic.ActivityInitConfig
import silladus.basic.IStatusBar


/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
object CommonActivityConfig {
    fun init(app: App) {

        val iStatusBar: IStatusBar = object : IStatusBar {
            override fun isClipToPadding(): Boolean {
                return true
            }

            override fun statusBarColor(): Int {
                return ContextCompat.getColor(app, R.color.colorAccent)
            }
        }

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(activity: Activity, p1: Bundle?) {

                ActivityInitConfig(activity, iStatusBar)

//                if (activity is FragmentActivity) {
//                    activity.supportFragmentManager
//                            .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
//                                override fun onFragmentCreated(
//                                        fm: FragmentManager,
//                                        f: Fragment,
//                                        savedInstanceState: Bundle?
//                                ) {
//                                }
//                            }, true)
//                }
            }

            override fun onActivityResumed(p0: Activity) {
            }

        })
    }
}