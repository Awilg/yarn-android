package com.orienteer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

class TreasureActivity : FragmentActivity() {

    /**
     * Our TreasureActivity is only responsible for setting the content view that contains the
     * Navigation Host.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TreasureActivity", "TESTING!")
        setContentView(R.layout.activity_treasure)
    }
}