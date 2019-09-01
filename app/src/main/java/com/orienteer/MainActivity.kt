package com.orienteer

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.orienteer.databinding.ActivityMainBinding
import com.orienteer.models.TreasureHunt
import timber.log.Timber.DebugTree
import timber.log.Timber
import java.io.File


class MainActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up Logger
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment_new_main)

        // Set up the app bar to use the nav graph and drawer
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        // Set up the toolbar
        val toolbar: Toolbar = binding.toolbarInclude.toolbar
        setSupportActionBar(toolbar)

        // This gets the header view at the first position of the NavigationView, which is the correct
        // header position that we want.
        binding.navView.getHeaderView(0).setOnClickListener {
            // TODO: Can we get this from a binding?
            navController.navigate(R.id.profileFragment)
            binding.drawerLayout.closeDrawers()
        }

        // Only allow the drawer on the top level map fragment. This is to handle whatever other nonsense
        // that NavigationUI can't do naturally, primarily dynamic actions to the toolbar.
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id != R.id.mapFragment) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

            if (destination.id == R.id.treasureHuntActiveFragment) {
                var hunt = arguments?.get("selectedTreasureHunt") as TreasureHunt
                binding.toolbarInclude.toolbar.title = hunt.name
            }
        }

        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
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

    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
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

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)

        var nightModeEnabled = false
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> nightModeEnabled = false
            Configuration.UI_MODE_NIGHT_YES -> nightModeEnabled = true
        }

        with (sharedPreferences.edit()) {
            putBoolean(getString(R.string.night_mode_enabled), nightModeEnabled)
            commit()
        }

    }


    companion object {
        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }
}