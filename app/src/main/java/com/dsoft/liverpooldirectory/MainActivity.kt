package com.dsoft.liverpooldirectory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dsoft.liverpooldirectory.databinding.ActivityMainmenuBinding
import com.dsoft.liverpooldirectory.ui.social.SocialViewModel
import com.dsoft.liverpooldirectory.utility.MyState
import com.dsoft.liverpooldirectory.utility.NetworkStatusTracker
import com.dsoft.liverpooldirectory.utility.map
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SocialViewModel>()
    private var _binding: ActivityMainmenuBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var networkStatusTracker: NetworkStatusTracker

    companion object {
        private const val VK_AUTH_REQUEST_CODE = 282
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainmenuBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragment))
        observeInternetStatus()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeInternetStatus() {
        val state = networkStatusTracker.networkStatus.map(
            onUnavailable = { MyState.Error },
            onAvailable = { MyState.Fetched }
        ).asLiveData(Dispatchers.IO)

        state.observe(this) { stateFlow ->
            val view = LayoutInflater.from(this).inflate(R.layout.fragment_no_internet_connection, null)
            when (stateFlow) {
                MyState.Fetched -> {}
                MyState.Error -> {
                    lifecycleScope.launch {
                        binding.containerNetwork.addView(view)
                        delay(3000)
                        view.animate().alpha(0f).duration = 300
                        delay(300)
                        binding.containerNetwork.removeAllViews()
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VK_AUTH_REQUEST_CODE) {
            if (data == null || !VK.onActivityResult(requestCode, resultCode, data, handleVkAuth())) {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun handleVkAuth(): VKAuthCallback {
        val currentTimeMillis = System.currentTimeMillis()
        return object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                val accessToken = token.accessToken
                with(viewModel) {
                    appPreferences.saveToken(accessToken)
                    appPreferences.saveTokenTime(currentTimeMillis)
                    interactor.isSuccess = true
                }
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(
                    applicationContext,
                    "Неудачная попытка авторизации",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}