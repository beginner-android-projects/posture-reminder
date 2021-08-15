package com.puntogris.posture.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.*
import com.puntogris.posture.R
import com.puntogris.posture.databinding.ActivityMainBinding
import com.puntogris.posture.ui.base.BaseActivity
import com.puntogris.posture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.viewModels
import com.puntogris.posture.model.AuthState
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    @Inject lateinit var dataStore: DataStore
    private val viewModel: MainViewModel by viewModels()

    override fun preInitViews() {
        setTheme(R.style.Theme_Posture)
        val theme = runBlocking { dataStore.appTheme() }
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    override fun initializeViews() {
        setupNavigation()

        viewModel.appVersionStatus.observe(this){ isNewVersion ->
            if (isNewVersion) navController.navigate(R.id.whatsNewDialog)
        }
    }

    private fun setupNavigation() {
        navController = getNavController()
        navController.graph = navController.navInflater.inflate(R.navigation.navigation)
            .apply {
                startDestination = when (viewModel.getAuthState()) {
                    AuthState.AuthComplete -> R.id.mainFragment
                    AuthState.AuthRequired -> R.id.loginFragment
                }
            }
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.mainFragment,
                    R.id.accountFragment,
                    R.id.newReminderBottomSheet,
                    R.id.welcomeFragment,
                    R.id.portalFragment,
                    R.id.loginFragment
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

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.welcomeFragment ||
            destination.id == R.id.newUserFragment ||
            destination.id == R.id.batteryOptimizationFragment ||
            destination.id == R.id.loginFragment
        ) {
            binding.bottomNavigation.gone()
        } else {
            binding.bottomNavigation.visible()
        }
    }

    override fun onStop() {
        viewModel.closeRealmInstance()
        super.onStop()
    }

    override fun onStart() {
        viewModel.instantiateRealmWithCurrentUser()
        super.onStart()
    }
}
