package com.grifalion.rickandmorty.presentation.fragments.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grifalion.rickandmorty.data.datasource.LocationDataSource
import com.grifalion.rickandmorty.domain.models.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn

class LocationListViewModel: ViewModel() {
    var locationFlow: Flow<PagingData<Location>> = emptyFlow()

    fun getLocations(name: String, type: String, dimension: String) {
        locationFlow = Pager(PagingConfig(pageSize = 1)) {
            LocationDataSource(name, type, dimension)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}