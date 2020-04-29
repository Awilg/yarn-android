package com.orienteer.treasurehuntdetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.orienteer.R
import com.orienteer.databinding.FragmentTreasureHuntDetailBinding
import com.rd.PageIndicatorView
import kotlinx.android.synthetic.main.detail_action_buttons.view.*


class TreasureHuntDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTreasureHuntDetailBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        val treasureHunt =
            TreasureHuntDetailFragmentArgs.fromBundle(arguments!!).selectedTreasureHunt
        val viewModelFactory = TreasureHuntDetailViewModelFactory(treasureHunt, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TreasureHuntDetailViewModel::class.java)

        binding.actionButtonSection.detail_active_button.setOnClickListener {
            // Must find the NavController from the Fragment
            this.findNavController().navigate(
                TreasureHuntDetailFragmentDirections.actionTreasureHuntDetailToTreasureHuntActiveFragment(
                    treasureHunt
                )
            )
        }

        // Set up the gallery viewpager
        val galleryAdapter = AdventureDetailImagePagerAdapter()
        val viewpager = binding.adventureMainGallery.adventureImgViewpager
        val pageIndicatorView: PageIndicatorView = binding.adventureMainGallery.pageIndicatorView
        viewpager.adapter = galleryAdapter
        viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pageIndicatorView.selection = position
            }
        })

        val appbar = binding.appbarLayout
        appbar.background.alpha = 0
        // This is to remove the elevation shadow while maintaining the elevation to draw
        // on top of the other views
        appbar.outlineProvider = null
        appbar.fitsSystemWindows = true

        val mToolbar = binding.myToolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(mToolbar)
        setHasOptionsMenu(true)
        mToolbar.title = null
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        mToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        adjustSystemUI()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
        activity!!.menuInflater.inflate(R.menu.app_bar_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(context, "Shared!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_favorite -> {
                Toast.makeText(context, "Favorite!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun adjustSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity!!.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}