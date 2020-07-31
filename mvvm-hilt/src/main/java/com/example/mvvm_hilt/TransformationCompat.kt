package com.example.mvvm_hilt

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.mvvm_hilt.ext.getActivity

/**
 * create by silladus 2020/7/31
 * github:https://github.com/silladus
 * des:
 */
object TransformationCompat {
    fun startActivity(transform: View, intent: (Activity) -> Intent) {
        val activity = transform.getActivity()
        requireNotNull(activity) { "The context parameter must be an activity's context!" }
        val bundle = ActivityOptions.makeSceneTransitionAnimation(activity, transform, "anim").toBundle()
        ActivityCompat.startActivity(activity, intent(activity), bundle)
    }
}