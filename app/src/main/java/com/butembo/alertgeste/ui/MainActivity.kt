package com.butembo.alertgeste.ui

import android.os.Bundle
import android.content.res.Configuration
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val dark = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        WindowCompat.getInsetsController(window, binding.root).apply {
            isAppearanceLightStatusBars = !dark
            isAppearanceLightNavigationBars = !dark
        }
        val nav = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        val roots = setOf(R.id.dashboardFragment, R.id.registerFragment, R.id.splashFragment)
        binding.toolbar.setupWithNavController(nav, AppBarConfiguration(roots))
        nav.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.visibility = if (destination.id in roots) View.GONE else View.VISIBLE
            binding.toolbar.title = getString(R.string.app_name)
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }
}
