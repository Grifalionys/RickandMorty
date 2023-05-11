package com.grifalion.rickandmorty.presentation.fragments.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import com.grifalion.rickandmorty.domain.usecases.location.GetListLocationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationListViewModel @Inject constructor(
    private val getListLocationsUseCase: GetListLocationsUseCase
) : ViewModel() {
    var locationFlow: Flow<PagingData<LocationResult>> = emptyFlow()

    fun getLocations(name: String, type: String, dimension: String) {
        locationFlow = Pager(PagingConfig(pageSize = 1)) {
            getListLocationsUseCase.execute(name,type,dimension)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}