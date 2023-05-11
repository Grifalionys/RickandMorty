package com.grifalion.rickandmorty.domain.usecases.location

import android.app.Application
import com.grifalion.rickandmorty.data.datasource.LocationDataSource
import com.grifalion.rickandmorty.domain.repository.LocationRepository
import javax.inject.Inject

class GetListLocationsUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val application: Application
) {
    fun execute(name: String, type: String, dimension: String): LocationDataSource {
        return LocationDataSource(repository, application, name, type, dimension)
    }
}