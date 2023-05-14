package com.grifalion.rickandmorty.data.mappers

import com.grifalion.rickandmorty.data.api.repsonse.episode.EpisodeResultResponse
import com.grifalion.rickandmorty.data.api.repsonse.location.LocationResultResponse
import com.grifalion.rickandmorty.data.db.entity.episode.EpisodeDbModel
import com.grifalion.rickandmorty.data.db.entity.location.LocationDbModel
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import org.junit.Assert
import org.junit.Test

class EpisodeMapperTest {
    private val episodeMapper = EpisodeMapper()

    @Test
    fun modelResponseForModelResult() {
        val episodeResultResponse = EpisodeResultResponse(
            air_date = EPISODE_AIR_DATE,
            characters = EPISODE_CHARACTERS,
            id = EPISODE_ID,
            name = EPISODE_NAME,
            episode = EPISODE_EPISODE,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val expectedEpisodeResult = EpisodeResult(
            air_date = EPISODE_AIR_DATE,
            characters = EPISODE_CHARACTERS,
            id = EPISODE_ID,
            name = EPISODE_NAME,
            episode = EPISODE_EPISODE,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val actualEpisodeResult : EpisodeResult = episodeMapper.mapResultsResponseForResults(episodeResultResponse)
        Assert.assertEquals(expectedEpisodeResult, actualEpisodeResult)
    }
    @Test
    fun modelResultForDbEpisodeModel() {

        val episodeResult = EpisodeResult(
            air_date = EPISODE_AIR_DATE,
            characters = EPISODE_CHARACTERS,
            id = EPISODE_ID,
            name = EPISODE_NAME,
            episode = EPISODE_EPISODE,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val expectedDbEpisode = EpisodeDbModel(
            air_date = EPISODE_AIR_DATE,
            episode = EPISODE_EPISODE,
            id = EPISODE_ID,
            name = EPISODE_NAME,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val actualDbEpisode: EpisodeDbModel = episodeMapper.mapEpisodeResultForEpisodeResultDb(episodeResult)
        Assert.assertEquals(expectedDbEpisode, actualDbEpisode)
    }

    @Test
    fun episodeDbModelForEpisodeResult() {
        val episodeDbModel = EpisodeDbModel(
            air_date = EPISODE_AIR_DATE,
            episode = EPISODE_EPISODE,
            id = EPISODE_ID,
            name = EPISODE_NAME,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val expectedEpisodeResult = EpisodeResult(
            air_date = EPISODE_AIR_DATE,
            characters = emptyList(),
            id = EPISODE_ID,
            name = EPISODE_NAME,
            episode = EPISODE_EPISODE,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )
        val actualEpisodeResult: EpisodeResult = episodeMapper.mapEpisodeResultDbForEpisodeResult(episodeDbModel)
        Assert.assertEquals(expectedEpisodeResult, actualEpisodeResult)
    }

    companion object {
        private const val EPISODE_ID = 123
        private const val EPISODE_NAME = "some name"
        private const val EPISODE_AIR_DATE = "some date"
        private const val EPISODE_EPISODE = "some episode"
        private val EPISODE_CHARACTERS = listOf(
            "https://test.com/api/character/1",
            "https://test.com/api/character/2",
            "https://test.com/api/character/3",
        )
        private const val EPISODE_URL = "some url"
        private const val EPISODE_CREATED = "some created"
    }
}