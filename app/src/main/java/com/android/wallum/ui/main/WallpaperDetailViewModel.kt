package com.android.wallum.ui.main

import android.app.Application
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.lifecycle.AndroidViewModel

/**
Created By Aditya Verma on 07/10/21
 **/

class WallpaperDetailViewModel(application: Application) : AndroidViewModel(application) {

    fun downloadWallpaper(url: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setDescription("Wallum")
        request.setTitle("$fileName.jpg")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        var manager: DownloadManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager =
                getApplication<Application>().getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        }
        manager?.enqueue(request)
    }

}