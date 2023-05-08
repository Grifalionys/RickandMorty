package com.grifalion.rickandmorty.domain.repository

import com.grifalion.rickandmorty.domain.models.episode.Episode

interface EpisodeRepository {

    suspend fun getEpisode(page: Int, name: String, episode: String): Episode

}