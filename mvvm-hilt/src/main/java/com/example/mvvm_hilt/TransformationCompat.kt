package com.example.mvvm_hilt

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import com.example.mvvm_hilt.ext.getActivity

/**
 * create by silladus 2020/7/31
 * github:https://github.com/silladus
 * des:
 */
object TransformationCompat {
    fun startActivity(transform: View, transitionName: String = "anim", intent: (Activity) -> Intent) {
        val activity = transform.getActivity()
        requireNotNull(activity) { "The context parameter must be an activity's context!" }
        transform.transitionName = transitionName
        val bundle = ActivityOptions.makeSceneTransitionAnimation(activity, transform, transitionName).toBundle()
        ActivityCompat.startActivity(activity, intent(activity), bundle)
    }

    fun applyTransformationParam(activity: Activity, transitionName: String = "anim") {
        ViewCompat.setTransitionName(activity.findViewById(android.R.id.content), transitionName)
    }
}