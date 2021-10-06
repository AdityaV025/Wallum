package com.android.wallum.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.wallum.R
import com.android.wallum.data.Wallpaper
import com.android.wallum.databinding.WallpaperItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
Created By Aditya Verma on 06/10/21
 **/

class WallpaperAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Wallpaper, WallpaperAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = WallpaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Wallpaper>() {
            override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Wallpaper,
                newItem: Wallpaper
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class PhotoViewHolder(private val binding: WallpaperItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item != null){
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: Wallpaper) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(wallpaperDisplay)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(wallpaper: Wallpaper)
    }

}