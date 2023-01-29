package com.CBPrograms.myfitnesslogger2023

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.CBPrograms.myfitnesslogger2023.services.keyboardService
import com.CBPrograms.myfitnesslogger2023.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(com.CBPrograms.myfitnesslogger2023.R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                com.CBPrograms.myfitnesslogger2023.R.id.navigation_weight_and_kfa,
                com.CBPrograms.myfitnesslogger2023.R.id.navigation_sleep_and_info,
                com.CBPrograms.myfitnesslogger2023.R.id.navigation_activity,
                com.CBPrograms.myfitnesslogger2023.R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        keyboardService.initialize(this)
    }
}
