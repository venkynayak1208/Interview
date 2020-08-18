package com.example.interview.task.view.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.venky.interview.`interface`.Connectivity
import com.venky.interview.utils.NetworkCheck

class NetworkChangeReceiver(private var listener: Connectivity) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        var boolean= NetworkCheck.isInternetAvailable(context)
        listener.onConnectec(boolean)


    }


}