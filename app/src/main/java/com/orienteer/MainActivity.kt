package com.orienteer

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
import com.orienteer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        // Only allow the drawer on the top level map fragment.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id != R.id.mapFragment) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
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
}