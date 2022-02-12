package com.dsoft.liverpooldirectory.utility

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.custom.MainButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment(@LayoutRes val layout: Int) : Fragment() {

    @Inject
    lateinit var networkStatusTracker: NetworkStatusTracker

    var hasInitializedRootView = false
    private var rootView: View? = null
    private var isOnline = false
        set(value) {
            field = value
            Log.d(this.tag, "isOnline: $value")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeInternetStatus()
    }

    fun getPersistentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout, container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }

    fun getScreenWidth(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        return (displayMetrics.widthPixels / displayMetrics.density).toInt()
    }

    fun safeCall(l: () -> Unit) {
        when (isOnline) {
            true -> l()
            false -> Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun observeInternetStatus() {
        val state = networkStatusTracker.networkStatus.map(
            onUnavailable = { MyState.Error },
            onAvailable = { MyState.Fetched }
        ).asLiveData(Dispatchers.IO)

        state.observe(this) { stateFlow ->
            if (stateFlow == null) return@observe

            isOnline = when (stateFlow) {
                MyState.Fetched -> true
                MyState.Error -> false
            }
        }
    }

}