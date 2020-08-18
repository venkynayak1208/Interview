package com.venky.interview.utils

import android.content.Context
import android.net.ConnectivityManager

import android.util.Log


 class NetworkCheck {

     companion object {
         private val TAG = NetworkCheck::class.java.simpleName
         @JvmStatic
         fun isInternetAvailable(context: Context?): Boolean {
             if (context != null) {
                 val info =
                     (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
                 return if (info == null) {
                     Log.d(TAG, "no internet connection")
                     false
                 } else {
                     if (info.isConnected) {
                         Log.d(TAG, " internet connection available...")
                         true
                     } else {
                         Log.d(TAG, " internet connection")
                         true
                     }
                 }
             }
             return false
         }
     }
}