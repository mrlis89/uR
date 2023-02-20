package com.arnava.ur.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.arnava.ur.App
import com.arnava.ur.R
import com.arnava.ur.databinding.ActivityMainBinding
import com.arnava.ur.ui.viewmodel.AuthViewModel
import com.arnava.ur.utils.connection.Connection
import com.arnava.ur.utils.connection.ConnectivityObserver
import com.arnava.ur.utils.connection.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()
    private var connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(App.appContext)
    private var initialRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_feed -> {
                    navController.navigate(R.id.navigation_feed)
                }
                R.id.navigation_favorite -> {
                    navController.navigate(R.id.navigation_favorite)
                }
                R.id.navigation_profile -> {
                    navController.navigate(R.id.navigation_profile)
                }
            }
            true
        }

        lifecycleScope.launchWhenCreated {
            connectivityObserver.observe().collect {
                when (it) {
                    ConnectivityObserver.Status.Available -> {
                        Connection.isAvailable = true
                        binding.navView.isVisible = true
                        authViewModel.refreshToken()
                        if (initialRun) initialRun = false
                        else Toast.makeText(
                                this@MainActivity,
                                "соединение снова доступно, вы можете перейти на любую страницу",
                                Toast.LENGTH_LONG
                            ).show()
                    }
                    ConnectivityObserver.Status.Unavailable -> {
                        binding.navView.isVisible = false
                        Connection.isAvailable = false
                        Toast.makeText(
                            this@MainActivity,
                            "соединение недоступно, загружены локальные данные",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(R.id.offlineDataFragment)
                    }
                    ConnectivityObserver.Status.Lost -> {
                        binding.navView.isVisible = false
                        Connection.isAvailable = false
                        Toast.makeText(
                            this@MainActivity,
                            "соединение потеряно, загружены локальные данные",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        navController.navigate(R.id.offlineDataFragment)
                    }
                }

            }
        }

        if (authViewModel.isFirstRun()) {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }

        if (connectivityObserver.isNetworkConnected()){
            if (authViewModel.hasRefreshToken()) {
                authViewModel.refreshToken()
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        } else {
            navController.navigate(R.id.offlineDataFragment)
            binding.navView.isVisible = false
        }

    }

}