package com.bofu.coroutinesretrofitmvvm.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isConnectedToNetwork(): Boolean {

    var connectionType = 0

    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    networkCapabilities?.run {
        if(hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            connectionType = 1
        } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            connectionType = 2
        }
    }


    when(connectionType){
        0 -> println("Not Connected")
        1 -> println("WIFI Connected")
        2 -> println("Cellular Connected")
    }


    return connectionType != 0
}