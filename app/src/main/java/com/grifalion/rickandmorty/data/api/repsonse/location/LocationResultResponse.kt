package com.grifalion.rickandmorty.data.api.repsonse.location

data class LocationResultResponse (
    val created: String?,
    val dimension: String?,
    val id: Int?,
    val name: String?,
    val residents: List<String>?,
    val type: String?,
    val url: String?
        )