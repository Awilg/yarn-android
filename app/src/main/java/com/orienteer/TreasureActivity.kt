package com.orienteer

import android.os.Bundle
import android.util.Log
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
    }
}