package com.lunatk.ledmaskapp.extension

import android.app.Activity
import android.util.Log
import com.lunatk.ledmaskapp.BuildConfig

fun Activity.logd(message: String){
    if(BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}

fun Activity.logv(message: String){
    if(BuildConfig.DEBUG) Log.v(this::class.java.simpleName, message)
}

fun Activity.logi(message: String){
    if(BuildConfig.DEBUG) Log.i(this::class.java.simpleName, message)
}
fun Activity.loge(message: String){
    if(BuildConfig.DEBUG) Log.e(this::class.java.simpleName, message)
}