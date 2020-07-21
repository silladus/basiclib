package com.example.mvvm_dagger.ext

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * create by silladus 2020/7/3
 * github:https://github.com/silladus
 * des:
 */
inline fun Context.toast(duration: Int = Toast.LENGTH_SHORT, value: () -> String?) = Toast.makeText(applicationContext, value(), duration).show()

inline fun Fragment.toast(duration: Int = Toast.LENGTH_SHORT, value: () -> String?) = this.context?.apply { toast(duration) { value() } }

inline fun View.toast(duration: Int = Toast.LENGTH_SHORT, value: () -> String?) = this.context?.apply { toast(duration) { value() } }