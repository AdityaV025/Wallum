package com.android.wallum.ui.main

import androidx.paging.PagingSource
import com.android.wallum.api.UnsplashApiInterface
import com.android.wallum.data.Wallpaper
import retrofit2.HttpException
import java.io.IOException

/**
Created By Aditya Verma on 06/10/21
 **/

private const val STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApiInterface,
    private val query: String
) : PagingSource<Int, Wallpaper>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        }
        catch (exception : IOException){
            LoadResult.Error(exception)
        }
        catch (exception : HttpException){
            LoadResult.Error(exception)
        }

    }

}