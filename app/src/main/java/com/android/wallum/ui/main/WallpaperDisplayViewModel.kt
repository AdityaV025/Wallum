package com.android.wallum.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.wallum.api.DataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
Created By Aditya Verma on 06/10/21
 **/

@HiltViewModel
class WallpaperDisplayViewModel @Inject constructor
    (
    private val repository: DataRepository,
    state: SavedStateHandle
) : ViewModel() {

    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "random"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val wallpaper = currentQuery.switchMap { query ->
        repository.getSearchPhotos(query).cachedIn(viewModelScope)
    }

    fun searchPhotos(query : String){
        currentQuery.value = query
    }

}