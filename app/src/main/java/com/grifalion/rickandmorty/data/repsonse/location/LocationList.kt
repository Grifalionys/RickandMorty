package com.grifalion.rickandmorty.data.repsonse.location

import com.grifalion.rickandmorty.domain.models.location.Location

data class LocationList (
    val info: Info,
    val results: ArrayList<Location>
)