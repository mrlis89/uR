package com.arnava.ur.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.arnava.ur.databinding.ActivityAuthBinding
import com.arnava.ur.databinding.ActivityOnboardingBinding
import com.arnava.ur.ui.adapter.ViewPagerFragmentAdapter
import com.arnava.ur.ui.fragment.onboarding.FirstFragment
import com.arnava.ur.ui.fragment.onboarding.SecondFragment
import com.arnava.ur.ui.fragment.onboarding.ThirdFragment
import com.arnava.ur.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels()

    private val authResponseResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            authViewModel.handleAuthResponseIntent(dataIntent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
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
                Toast.makeText(this@AuthActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launchWhenStarted {
            authViewModel.authSuccessFlow.collect {
                Toast.makeText(this@AuthActivity, "auth success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AuthActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateVisibility(isLoading: Boolean) {
        binding.loginButton.isVisible = !isLoading
        binding.loginProgress.isVisible = isLoading
    }
}