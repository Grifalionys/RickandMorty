package com.grifalion.rickandmorty.data.mappers

import com.grifalion.rickandmorty.data.api.repsonse.location.LocationResultResponse
import com.grifalion.rickandmorty.data.db.entity.location.LocationDbModel
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import org.junit.Assert
import org.junit.Test

class LocationMapperTest {
    private val locationMapper = LocationMapper()

    @Test
    fun modelResponseForModelResult() {
        val locationResultResponse = LocationResultResponse(
            id = LOCATION_ID,
            name = LOCATION_NAME,
            url = LOCATION_URL,
            created = LOCATION_CREATED,
            residents = LOCATION_CHARACTERS,
            type = LOCATION_TYPE,
            dimension = LOCATION_DIMENSION
        )

        val expectedLocationResult = LocationResult(
            id = LOCATION_ID,
            name = LOCATION_NAME,
            url = LOCATION_URL,
            created = LOCATION_CREATED,
            residents = LOCATION_CHARACTERS,
            type = LOCATION_TYPE,
            dimension = LOCATION_DIMENSION
        )

        val actualLocationResult : LocationResult = locationMapper.mapResultsResponseForResults(locationResultResponse)
        Assert.assertEquals(expectedLocationResult, actualLocationResult)
    }
    @Test
    fun modelResultForDbLocationModel() {

        val locationResult = LocationResult(
            id = LOCATION_ID,
            name = LOCATION_NAME,
            url = LOCATION_URL,
            created = LOCATION_CREATED,
            residents = LOCATION_CHARACTERS,
            type = LOCATION_TYPE,
            dimension = LOCATION_DIMENSION
        )

        val expectedDbLocation = LocationDbModel(
            id = LOCATION_ID,
            name = LOCATION_NAME,
            url = LOCATION_URL,
            created = LOCATION_CREATED,
            dimension = LOCATION_DIMENSION,
            type = LOCATION_TYPE
        )

        val actualDbLocation: LocationDbModel = locationMapper.mapLocationResultForLocationResultDb(locationResult)
        Assert.assertEquals(expectedDbLocation, actualDbLocation)
    }

    @Test
    fun locationDbModelForLocationResult() {
        val locationDbModel = LocationDbModel(
            id = LOCATION_ID,
            name = LOCATION_NAME,
            url = LOCATION_URL,
            created = LOCATION_CREATED,
            dimension = LOCATION_DIMENSION,
            type = LOCATION_TYPE
        )

        val expectedLocationResult = LocationResult(
            id = LOCATION_ID,
            name = LOCATION_NAME,
            url = LOCATION_URL,
            created = LOCATION_CREATED,
            residents = emptyList(),
            type = LOCATION_TYPE,
            dimension = LOCATION_DIMENSION
        )
        val actualLocationResult: LocationResult = locationMapper.mapLocationResultDbForLocationResult(locationDbModel)
        Assert.assertEquals(expectedLocationResult, actualLocationResult)
    }

    companion object {
        private const val LOCATION_ID = 123
        private const val LOCATION_NAME = "some name"
        private const val LOCATION_TYPE = "some type"
        private const val LOCATION_DIMENSION = "some dimension"
        private val LOCATION_CHARACTERS = listOf(
            "https://test.com/api/character/1",
            "https://test.com/api/character/2",
            "https://test.com/api/character/3",
        )
        private const val LOCATION_URL = "some url"
        private const val LOCATION_CREATED = "some created"
    }
}