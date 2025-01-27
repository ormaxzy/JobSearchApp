package com.example.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as androidx.navigation.fragment.NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        observeFavoriteVacancies(bottomNavigationView)
    }

    private fun observeFavoriteVacancies(bottomNavigationView: BottomNavigationView) {
        viewModel.favoriteVacancyCount.observe(this) { count ->
            val badge = bottomNavigationView.getOrCreateBadge(R.id.favoritesFragment)
            if (count > 0) {
                badge.isVisible = true
                badge.number = count
                badge.backgroundColor = getColor(R.color.red)
                badge.badgeTextColor = getColor(R.color.white)
            } else {
                badge.isVisible = false
            }
        }
    }
}
