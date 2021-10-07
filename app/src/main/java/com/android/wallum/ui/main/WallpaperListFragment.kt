package com.android.wallum.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.wallum.R
import com.android.wallum.data.Wallpaper
import com.android.wallum.databinding.FragmentWallpaperListBinding
import com.android.wallum.ui.main.adapter.WallpaperAdapter
import com.android.wallum.utils.Util.Companion.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

/**
Created By Aditya Verma on 06/10/21
 **/

@AndroidEntryPoint
class WallpaperListFragment : Fragment(R.layout.fragment_wallpaper_list),
    WallpaperAdapter.OnItemClickListener {

    private val viewModel by viewModels<WallpaperDisplayViewModel>()
    private var _binding: FragmentWallpaperListBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWallpaperListBinding.bind(view)
        val adapter = WallpaperAdapter(this)

        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter

            if (!isNetworkConnected()){
                noConnectionLayout.root.isVisible = true
                recyclerview.isVisible = false
            }

            noConnectionLayout.retryConnectionButton.setOnClickListener {
                if (isNetworkConnected()){
                    noConnectionLayout.root.isVisible = false
                    viewModel.searchPhotos("random")
                    recyclerview.isVisible = true
                }
            }

            editQueryText.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (event != null) {
                        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                            viewModel.searchPhotos(editQueryText.text.toString())
                            hideKeyboard()
                            editQueryText.clearFocus()
                            return true
                        }
                    }
                    return false
                }
            })

        }

        viewModel.wallpaper.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onItemClick(wallpaper: Wallpaper) {
        val action = WallpaperListFragmentDirections.actionWallpaperListFragmentToWallpaperDetailFragment(wallpaper)
        findNavController().navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkConnected(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}