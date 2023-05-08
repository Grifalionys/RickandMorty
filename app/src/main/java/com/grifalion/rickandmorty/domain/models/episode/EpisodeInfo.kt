package com.grifalion.rickandmorty.domain.models.episode

data class EpisodeInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
    )
