package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.LocationApiService
import com.grifalion.rickandmorty.data.db.dao.LocationDao
import com.grifalion.rickandmorty.data.mappers.LocationMapper
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.location.Location
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import com.grifalion.rickandmorty.domain.repository.LocationRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationApiService,
    private val locationDao: LocationDao,
    val mapper: LocationMapper
): LocationRepository {

    override suspend fun getLocation(
        page: Int,
        name: String,
        type: String,
        dimension: String,
    ): Location {
        var locationApi = apiService.getLocation(page, name, type, dimension)
        val listLocations = mapper.mapLocationResponseForLocation(locationApi)
        locationDao.insertLocation(mapper.mapListResultResponseForListDb(listLocations.results))
        return listLocations
    }

    override suspend fun insertLocation(list: List<LocationResult>) {
        locationDao.insertLocation(mapper.mapListResultResponseForListDb(list))
    }

    override fun getListLocationsDb(): List<LocationResult> {
        var listLocations = emptyList<LocationResult>()
        CoroutineScope(Dispatchers.IO).launch {
            listLocations = (locationDao.getAllLocation()).map {
                mapper.mapLocationResultDbForLocationResult(it)
            }
        }
        return listLocations
    }

    override fun getListCharactersIntoLocationDetail(id: String): Observable<List<CharacterResult>> {
        return apiService.getListCharactersByIdIntoLocationDetail(id)
    }
}