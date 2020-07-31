package com.example.mvvm_hilt.ext

import android.app.Activity
import android.content.ContextWrapper
import android.view.View

/**
 * create by silladus 2020/7/29
 * github:https://github.com/silladus
 * des:
 */
fun View.gone(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

/** gets an activity from a context. */
internal fun View.getActivity(): Activity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}