package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.api.EpisodeApiService
import com.grifalion.rickandmorty.data.mappers.EpisodeMapper
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.repository.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val apiService: EpisodeApiService,
    private val mapper: EpisodeMapper
): EpisodeRepository {

    override suspend fun getEpisode(page: Int, name: String, episode: String): Episode {
        val listEpisodes = apiService.getEpisode(page,name,episode)
        return mapper.mapEpisodeResponseForEpisode(listEpisodes)
    }
}