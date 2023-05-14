package com.grifalion.rickandmorty.data.mappers

import com.grifalion.rickandmorty.data.api.repsonse.episode.EpisodeInfoResponse
import com.grifalion.rickandmorty.data.api.repsonse.episode.EpisodeResponse
import com.grifalion.rickandmorty.data.api.repsonse.episode.EpisodeResultResponse
import com.grifalion.rickandmorty.data.db.entity.episode.EpisodeDbModel
import com.grifalion.rickandmorty.data.db.entity.location.LocationDbModel
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.models.episode.EpisodeInfo
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import javax.inject.Inject

class EpisodeMapper @Inject constructor() {
    private fun mapInfoResponseForInfo(infoResponse: EpisodeInfoResponse?) = EpisodeInfo(
        count = infoResponse?.count ?: ZERO_NUMBER,
        next = infoResponse?.next ?: EMPTY_STRING,
        pages = infoResponse?.pages ?: ZERO_NUMBER,
        prev = infoResponse?.prev ?: EMPTY_STRING
    )

    fun mapResultsResponseForResults(resultResponse: EpisodeResultResponse?) = EpisodeResult(
        air_date = resultResponse?.air_date ?: EMPTY_STRING,
        characters = resultResponse?.characters ?: emptyList(),
        created = resultResponse?.created ?: EMPTY_STRING,
        episode = resultResponse?.episode ?: EMPTY_STRING,
        id = resultResponse?.id ?: ZERO_NUMBER,
        name = resultResponse?.name ?: EMPTY_STRING,
        url = resultResponse?.url ?: EMPTY_STRING
    )

    private fun mapListResultsResponseForListResults(list: List<EpisodeResultResponse>) = list.map {
        mapResultsResponseForResults(it)
    }

    fun mapEpisodeResponseForEpisode(locationDto: EpisodeResponse) = Episode(
        info = mapInfoResponseForInfo(locationDto.info),
        results = mapListResultsResponseForListResults(locationDto.results)
    )

    fun mapEpisodeResultForEpisodeResultDb(episodeResult: EpisodeResult): EpisodeDbModel {
        return EpisodeDbModel (
            air_date = episodeResult.air_date,
            created = episodeResult.created,
            episode = episodeResult.episode,
            id = episodeResult.id,
            name = episodeResult.name,
            url = episodeResult.url
        )
    }

    fun mapListResultResponseForListDb(list: List<EpisodeResult>) = list.map {
        mapEpisodeResultForEpisodeResultDb(it)
    }

    fun mapEpisodeResultDbForEpisodeResult(episodeResult: EpisodeDbModel): EpisodeResult{
        return EpisodeResult(
            air_date = episodeResult.air_date,
            characters = emptyList(),
            episode = episodeResult.episode,
            created = episodeResult.created,
            id = episodeResult.id,
            name = episodeResult.name,
            url = episodeResult.url
        )
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val ZERO_NUMBER = 0
    }
}