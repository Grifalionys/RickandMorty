package com.grifalion.rickandmorty.domain.models.location


data class Location(
    val info: LocationInfo,
    val results: List<LocationResult>
)
