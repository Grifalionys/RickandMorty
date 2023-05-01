package com.grifalion.rickandmorty.data.api.repsonse.location

import com.grifalion.rickandmorty.domain.models.location.Location

data class LocationResponse (
    val info: Info,
    val results: ArrayList<Location>
)