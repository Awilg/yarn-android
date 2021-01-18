package com.orienteer.treasurehuntdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.databinding.DetailGalleryImgItemBinding

class AdventureDetailImagePagerAdapter(var imageUrls: List<String> = mutableListOf()) :
    RecyclerView.Adapter<PagerImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerImageViewHolder {
        return PagerImageViewHolder(
            DetailGalleryImgItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PagerImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    fun updateGalleryImages(newImageUrls: List<String>) {
        this.imageUrls = newImageUrls
        this.notifyDataSetChanged()
    }
}

class PagerImageViewHolder(private val binding: DetailGalleryImgItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(imgUrl: String) {
        binding.imageUrl = imgUrl
    }
}