package com.arnava.ur.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.arnava.ur.databinding.ActivityMainBinding
import com.arnava.ur.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()

    private val authResponseResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            authViewModel.handleAuthResponseIntent(dataIntent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            authViewModel.authorizeAndWait(authResponseResultLauncher)
        }
        lifecycleScope.launchWhenStarted {
            authViewModel.loadingFlow.collect {
                updateVisibility(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            authViewModel.toastFlow.collect {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launchWhenStarted {
            authViewModel.authSuccessFlow.collect {
                Toast.makeText(this@MainActivity, "auth success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateVisibility(isLoading: Boolean) {
        binding.loginButton.isVisible = !isLoading
        binding.loginProgress.isVisible = isLoading
    }
}