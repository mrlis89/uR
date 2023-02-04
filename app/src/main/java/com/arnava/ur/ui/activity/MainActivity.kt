package com.arnava.ur.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.arnava.ur.R
import com.arnava.ur.databinding.ActivityMainBinding
import com.arnava.ur.ui.viewmodel.AuthViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()

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

    }

}