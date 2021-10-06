package com.android.wallum.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.android.wallum.ui.main.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

/**
Created By Aditya Verma on 06/10/21
 **/

@Singleton
class DataRepository @Inject constructor(private val unsplashApi: UnsplashApiInterface) {

    fun getSearchPhotos(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData

}