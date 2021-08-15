package com.puntogris.posture.ui.main

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.*
import com.puntogris.posture.R
import com.puntogris.posture.databinding.ActivityMainBinding
import com.puntogris.posture.ui.base.BaseActivity
import com.puntogris.posture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import android.graphics.BlendMode

import android.graphics.BlendModeColorFilter
import androidx.lifecycle.lifecycleScope
import com.puntogris.posture.BuildConfig
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    @Inject
    lateinit var dataStore: DataStore

    override fun preInitViews() {
        setTheme(R.style.Theme_Posture)
        val theme = runBlocking { dataStore.appTheme() }
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    override fun initializeViews() {
        setupNavigation()

        lifecycleScope.launch {
            if (dataStore.lastVersionCode() < BuildConfig.VERSION_CODE){
                dataStore.updateLastVersionCode()
                navController.navigate(R.id.whatsNewDialog)
            }
        }
    }

    private fun setupNavigation() {
        navController = getNavController()
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.mainFragment,
                    R.id.accountFragment,
                    R.id.newReminderBottomSheet,
                    R.id.welcomeFragment,
                    R.id.healthFragment
                )
            )
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener {}
        }

        navController.addOnDestinationChangedListener(this@MainActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newReminder -> {
                navController.navigate(R.id.newReminderBottomSheet)
                true
            }
            R.id.settings -> {
                navController.navigate(R.id.settingsBottomSheet)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    @Suppress("DEPRECATION")
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.welcomeFragment ||
            destination.id == R.id.newUserFragment ||
            destination.id == R.id.batteryOptimizationFragment
        ) {
            val backgroundColor =  getColor(R.color.dayBackground)
            window.statusBarColor = backgroundColor
            binding.toolbar.navigationIcon?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    it.colorFilter = BlendModeColorFilter(getColor(R.color.black), BlendMode.SRC_ATOP)
                } else {
                    it.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
                }
            }
            binding.toolbar.setBackgroundColor(backgroundColor)
            binding.bottomNavigation.gone()

            WindowInsetsControllerCompat(window, window.decorView)
                .isAppearanceLightStatusBars = false
        } else {
            val backgroundColor = if (isDarkThemeOn()) getColor(R.color.nightBackground) else getColor(R.color.dayBackground)

            binding.toolbar.setBackgroundColor(backgroundColor)
            binding.bottomNavigation.visible()
            window.statusBarColor = backgroundColor

            WindowInsetsControllerCompat(window, window.decorView)
                .isAppearanceLightStatusBars = !isDarkThemeOn()
        }
    }
}
