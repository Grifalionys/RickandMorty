package com.grifalion.rickandmorty.domain.repository

import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult

interface EpisodeRepository {

    suspend fun getEpisode(page: Int, name: String, episode: String): Episode

    suspend fun insertEpisode(list: List<EpisodeResult>)

    fun getListEpisodesDb(): List<EpisodeResult>

}