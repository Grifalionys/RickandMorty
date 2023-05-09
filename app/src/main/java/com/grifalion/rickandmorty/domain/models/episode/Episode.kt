package com.grifalion.rickandmorty.domain.models.episode


data class Episode (
    val info: EpisodeInfo,
    val results: List<EpisodeResult>
        )