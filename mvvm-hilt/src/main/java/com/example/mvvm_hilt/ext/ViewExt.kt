package com.example.mvvm_hilt.ext

import android.view.View

/**
 * create by silladus 2020/7/29
 * github:https://github.com/silladus
 * des:
 */
fun View.gone(isGone:Boolean){
    visibility = if (isGone) {
        View.GONE
    } else{
        View.VISIBLE
    }
}