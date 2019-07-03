package com.orienteer

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.orienteer.databinding.ActivityTreasureBinding

class TreasureActivity : AppCompatActivity() {

    /**
     * Our TreasureActivity is only responsible for setting the content view that contains the
     * Navigation Host.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TreasureActivity", "TESTING!")
        val binding = DataBindingUtil.setContentView<ActivityTreasureBinding>(this, R.layout.activity_treasure)
        // Set up the action bar
        val toolbar: Toolbar = binding.toolbarInclude.toolbar
        setSupportActionBar(toolbar)

        // Set the action bar to have a back button showing
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Destroy this activity and return to previous activity on the stack.
        if (item.itemId == android.R.id.home)
        {
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }
}