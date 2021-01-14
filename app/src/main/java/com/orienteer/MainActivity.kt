package com.orienteer

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksViewModelConfigFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.File

class MainActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mavericks.initialize(this)
        Mavericks.viewModelConfigFactory = MavericksViewModelConfigFactory(applicationContext)

        setContentView(R.layout.activity_main)
        // Set up Logger
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        setupNav()

        hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (
                SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar

                        // Set the light theme status bar
                        or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //
        // Using the onNavDestinationSelected helper requires the menu
        // item id and the navigation destination id to match in order
        // for this to work.
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        // Instantiate the new Fragment
        val args = pref.extras
        // Need to get the fragment manager of the child and not the nav host
        val fragManager = supportFragmentManager.primaryNavigationFragment!!.childFragmentManager
        val fragment = fragManager.fragmentFactory.instantiate(classLoader, pref.fragment).apply {
            arguments = args
            setTargetFragment(caller, 0)
        }
        // Replace the existing Fragment with the new Fragment
        fragManager.beginTransaction()
            .replace(R.id.settings, fragment)
            .addToBackStack(null)
            .commit()
        title = pref.title
        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)

        var nightModeEnabled = false
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> nightModeEnabled = false
            Configuration.UI_MODE_NIGHT_YES -> nightModeEnabled = true
        }

        with(sharedPreferences.edit()) {
            putBoolean(getString(R.string.night_mode_enabled), nightModeEnabled)
            commit()
        }

    }

    private fun setupNav() {
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.exploreFragment -> showBottomNav()
                R.id.exploreFragmentV2 -> showBottomNav()
                R.id.profileFragment -> showBottomNav()
                R.id.advCreateFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottomNavigationView.visibility = VISIBLE

    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = GONE

    }

    companion object {
        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
            }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }
}