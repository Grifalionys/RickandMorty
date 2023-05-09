package com.grifalion.rickandmorty.data.api.repsonse.episode

data class EpisodeResultResponse (
    val air_date: String?,
    val characters: List<String>?,
    val created: String?,
    val episode: String?,
    val id: Int?,
    val name: String?,
    val url: String?
)