package com.android.wallum.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallpaper(
    val id: String,
    val user: UnsplashUser,
    val urls: WallpaperUrls,
    val links: WallpaperDownload
) : Parcelable {

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearch&utm_medium=referral"
    }

    @Parcelize
    data class WallpaperUrls(
        val regular: String,
        val full: String,
        val raw: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class WallpaperDownload(
        val download: String
    ) : Parcelable

}
