package com.grifalion.rickandmorty.data.repsonse.episode

import com.grifalion.rickandmorty.domain.models.episode.Episode

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)
