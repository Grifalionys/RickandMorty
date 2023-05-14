package com.grifalion.rickandmorty.data.mappers

import com.grifalion.rickandmorty.data.db.entity.episode.EpisodeDbModel
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import org.junit.Assert
import org.junit.Test

class EpisodeMapperTest {
    private val episodeMapper = EpisodeMapper()

    @Test
    fun modelToDbEpisode() {

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


        val actualDbEpisode: EpisodeDbModel =
            episodeMapper.mapEpisodeResultForEpisodeResultDb(episodeResult)

        Assert.assertEquals(expectedDbEpisode, actualDbEpisode)

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