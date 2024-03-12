package com.dsoft.liverpooldirectory2.utility

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment(@LayoutRes val layout: Int) : Fragment(layout) {

    @Inject
    lateinit var networkStatusTracker: NetworkStatusTracker

    private var isOnline = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeInternetStatus()
    }

    fun getScreenWidth(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        return (displayMetrics.widthPixels / displayMetrics.density).toInt()
    }

    fun safeCall(l: () -> Unit) {
        when (isOnline) {
            true -> l()
            false -> Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeInternetStatus() {
        val state = networkStatusTracker.networkStatus.map(
            onUnavailable = { NetworkStatusTracker.NetworkState.Error },
            onAvailable = { NetworkStatusTracker.NetworkState.Fetched }
        ).asLiveData(Dispatchers.IO)

        state.observe(this) { stateFlow ->
            if (stateFlow == null) return@observe

            isOnline = when (stateFlow) {
                NetworkStatusTracker.NetworkState.Fetched -> true
                NetworkStatusTracker.NetworkState.Error -> false
            }
        }
    }

}