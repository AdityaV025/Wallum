package com.android.wallum.ui.main

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.android.wallum.MainActivity
import com.android.wallum.R
import com.android.wallum.databinding.FragmentWallpaperDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URL

/**
Created By Aditya Verma on 07/10/21
 **/

class WallpaperDetailFragment : Fragment(R.layout.fragment_wallpaper_detail) {

    private val args by navArgs<WallpaperDetailFragmentArgs>()
    private val viewModel by viewModels<WallpaperDetailViewModel>()
    private lateinit var progressBar : ProgressBar
    private var _binding : FragmentWallpaperDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWallpaperDetailBinding.bind(view)

        progressBar = binding.progressbar

        binding.apply {
            val wallpaper = args.wallpaper
            Glide.with(this@WallpaperDetailFragment)
                .load(wallpaper.urls.full)
                .centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.isVisible = false
                        return false
                    }

                })
                .into(wallpaperFullDisplay)

            infoButton.setOnClickListener {
                Snackbar.make(
                    view,
                    "Photo by ${wallpaper.user.name} on Unsplash",
                    Snackbar.LENGTH_SHORT
                )
                    .setBackgroundTint(resources.getColor(R.color.primary))
                    .setTextColor(resources.getColor(R.color.white))
                    .setAction("Visit") {
                        val uri = Uri.parse(wallpaper.user.attributionUrl)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                    .show()
            }

            downloadButton.setOnClickListener {
                viewModel.downloadWallpaper(wallpaper.links.download, wallpaper.user.username)
                Toast.makeText(context, "Download Started", Toast.LENGTH_SHORT).show()
            }

            setWallpaperButton.setOnClickListener {
                progressbar.isVisible = true
                setWallpaper(wallpaper.urls.full)
            }
        }

    }

    private fun setWallpaper(url: String){
        Glide.with(this)
            .asBitmap()
            .load(Uri.parse(url))
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val wallpaperManager : WallpaperManager = WallpaperManager.getInstance(context)
                    wallpaperManager.setBitmap(resource)
                    progressBar.isVisible = false
                    Toast.makeText(context, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}