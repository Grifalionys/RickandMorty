package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.LocationApiService
import com.grifalion.rickandmorty.data.mappers.LocationMapper
import com.grifalion.rickandmorty.domain.models.location.Location
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import com.grifalion.rickandmorty.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationApiService,
    val mapper: LocationMapper
): LocationRepository {

    override suspend fun getLocation(
        page: Int,
        name: String,
        type: String,
        dimension: String,
    ): Location {
        var locationList = apiService.getLocation(page, name, type, dimension)
        return mapper.mapLocationResponseForLocation(locationList)
    }

    override suspend fun insertLocation(list: List<LocationResult>) {
        TODO("Not yet implemented")
    }

}