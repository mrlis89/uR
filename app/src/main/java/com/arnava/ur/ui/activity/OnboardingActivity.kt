package com.arnava.ur.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.arnava.ur.databinding.ActivityOnboardingBinding
import com.arnava.ur.ui.adapter.ViewPagerFragmentAdapter
import com.arnava.ur.ui.fragment.onboarding.FirstFragment
import com.arnava.ur.ui.fragment.onboarding.SecondFragment
import com.arnava.ur.ui.fragment.onboarding.ThirdFragment

class OnboardingActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_onboarding) as NavHostFragment
//        val navController = navHostFragment.navController

        binding.viewPager.adapter = ViewPagerFragmentAdapter(
            this, listOf(
                FirstFragment(),
                SecondFragment(),
                ThirdFragment()
            )
        )
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.viewPager)
    }
}