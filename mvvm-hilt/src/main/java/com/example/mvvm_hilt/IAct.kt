package com.example.mvvm_hilt

import silladus.basic.ActivityInitConfig
import silladus.basic.IActivity

/**
 * create by silladus 2020/7/22
 * github:https://github.com/silladus
 * des:
 */
interface IAct : IActivity {
    override fun getLayoutRes(): Int = 0

    override fun onConfigInit(config: ActivityInitConfig) {
    }
}