package demo.app.demotechno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import demo.app.demotechno.databinding.ActivityMainBinding
import demo.app.demotechno.utils.SharedPreferencesHelper

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
        val bottomNav = binding.navView
        bottomNav.setupWithNavController(navController)

        AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.staticsFragment,
                R.id.profileFragment
            )
        )

        if (SharedPreferencesHelper.isUserSignedIn(this)) {
            // Go to home page
            navigateToHomeFragment()
        }  else {
            // Go to onboarding page
            navigateToOnboardingFragment()
        }




        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.homeFragment || destination.id == R.id.staticsFragment
                ||  destination.id == R.id.profileFragment
            ) {
                binding.navView.visibility = View.VISIBLE
            } else {
                binding.navView.visibility = View.GONE
            }
        }
    }
    private fun navigateToHomeFragment() {
        // Implement navigation to home fragment using Navigation Component
        // For example:
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.homeFragment)
        navController.graph = navGraph
    }


    private fun navigateToOnboardingFragment() {
        // Implement navigation to onboarding fragment using Navigation Component
        // For example:
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.onBoardingFragment)
        navController.graph = navGraph
    }
}