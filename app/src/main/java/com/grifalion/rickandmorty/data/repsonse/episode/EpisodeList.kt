package com.grifalion.rickandmorty.data.repsonse.episode

import com.grifalion.rickandmorty.domain.models.episode.Episode

data class EpisodeList(
    val info: Info,
    val results: List<Episode>
)
