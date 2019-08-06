package com.ambe.tekotest.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 *  Created by AMBE on 6/8/2019 at 16:12 PM.
 */

object Utils {

    fun checkInternetConnection(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity == null) {
            return false
        } else {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (anInfo in info) {
                    if (anInfo.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
}