package com.grifalion.rickandmorty.data.api.repsonse.episode

import com.grifalion.rickandmorty.domain.models.episode.Episode

data class EpisodeResponse(
    val info: EpisodeInfoResponse,
    val results: List<EpisodeResultResponse>
)
