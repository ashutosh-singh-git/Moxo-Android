package com.rom.moxo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData


class ConnectivityManager(context: Context) : LiveData<Boolean>() {

    val TAG = "C-Manager"

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() =
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                Log.d(TAG, "onAvailable: $network")
                val networkCapabilities = cm.getNetworkCapabilities(network)
                val isInternet =
                    networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (isInternet == true) {
                    validNetworks.add(network)
                }
                checkValidNetworks()
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "onLost: $network")
                validNetworks.remove(network)
                checkValidNetworks()
            }
        }
}