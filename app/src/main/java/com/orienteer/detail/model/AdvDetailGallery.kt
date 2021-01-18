package com.orienteer.detail.model

import androidx.viewpager2.widget.ViewPager2
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.orienteer.R
import com.orienteer.treasurehuntdetail.AdventureDetailImagePagerAdapter
import com.orienteer.util.KotlinEpoxyHolder
import com.rd.PageIndicatorView

@EpoxyModelClass(layout = R.layout.e_detail_gallery)
abstract class AdvDetailGallery : EpoxyModelWithHolder<AdvDetailGallery.Holder>() {

    @EpoxyAttribute
    var adventureImages: List<String> = emptyList()

    lateinit var callback: ViewPager2.OnPageChangeCallback

    override fun bind(holder: Holder) {
        // Set up the gallery viewpager
        callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                holder.pageIndicator.selection = position
            }
        }
        val galleryAdapter = AdventureDetailImagePagerAdapter()
        holder.viewPager.adapter = galleryAdapter
        galleryAdapter.imageUrls = adventureImages
        holder.viewPager.registerOnPageChangeCallback(callback)
    }

    override fun unbind(holder: Holder) {
        holder.viewPager.adapter = null
        holder.viewPager.unregisterOnPageChangeCallback(callback)
    }

    class Holder : KotlinEpoxyHolder() {
        val viewPager by bind<ViewPager2>(R.id.adventure_img_viewpager)
        val pageIndicator by bind<PageIndicatorView>(R.id.pageIndicatorView)
    }
}
