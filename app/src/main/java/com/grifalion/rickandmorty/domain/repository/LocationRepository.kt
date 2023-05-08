package com.grifalion.rickandmorty.domain.repository

import com.grifalion.rickandmorty.domain.models.location.Location
import com.grifalion.rickandmorty.domain.models.location.LocationResult

interface LocationRepository {

    suspend fun getLocation(page: Int, name: String, type: String, dimension: String): Location

    suspend fun insertLocation(list: List<LocationResult>)

}