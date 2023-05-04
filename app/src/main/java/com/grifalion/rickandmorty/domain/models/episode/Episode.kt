package com.grifalion.rickandmorty.domain.models.episode


data class Episode (
    val id: Int,
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
        )