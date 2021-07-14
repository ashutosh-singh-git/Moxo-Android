package com.rom.moxo.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.rom.moxo.R
import com.rom.moxo.databinding.ActivityMainBinding
import com.rom.moxo.utils.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import com.rom.moxo.utils.isOnline


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityManager: ConnectivityManager

    private var snackBar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        connectivityManager = ConnectivityManager(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        connectivityManager.observe(this, { isNetworkAvailable ->
            connection(isNetworkAvailable = isNetworkAvailable)
        })

        val toolbar: Toolbar = binding.app.toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            setUpBottomNavigationBar()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isOnline) {
                snackBar = Snackbar.make(
                    binding.root,
                    "You are offline",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBar?.setAction("DISMISS", View.OnClickListener {
                    snackBar?.dismiss();
                })
                snackBar?.show()
            }
        }, 1500);
    }

    private fun connection(isNetworkAvailable: Boolean) {
        if (!isNetworkAvailable) {
            isOnline = false
            snackBar = Snackbar.make(
                binding.root,
                "You are offline",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar?.setAction("DISMISS", View.OnClickListener {
                snackBar?.dismiss();
            })
            snackBar?.show()
        } else {
            isOnline = true
            snackBar?.dismiss()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() {
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        bottomNavigationView = binding.app.content.bottomNavView


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_user, R.id.navigation_blog,
                R.id.navigation_journey, R.id.navigation_activity
            ), drawerLayout
        )

        navigationView.setupWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_settings -> hideBottomNav()
                R.id.navigation_terms -> hideBottomNav()

                else -> showBottomNav()
            }
        }
    }


    private fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }
}