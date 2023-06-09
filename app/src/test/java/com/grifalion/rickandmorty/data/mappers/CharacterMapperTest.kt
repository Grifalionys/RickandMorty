package com.grifalion.rickandmorty.data.mappers

import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterLocationResponse
import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterOriginResponse
import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterResultResponse
import com.grifalion.rickandmorty.data.db.entity.character.CharacterDbModel
import com.grifalion.rickandmorty.domain.models.character.CharacterLocation
import com.grifalion.rickandmorty.domain.models.character.CharacterOrigin
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import org.junit.Assert.assertEquals
import org.junit.Test


class CharacterMapperTest {
    private val characterMapper = CharacterMapper()

    @Test
    fun modelResponseForModelResult() {
        val characterResultResponse = CharacterResultResponse(
            created = CHARACTER_CREATED,
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = CharacterOriginResponse(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = CharacterLocationResponse(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            image = CHARACTER_IMAGE,
            episode = CHARACTER_EPISODES,
            url = CHARACTER_URL,
        )

        val expectedCharacterResult = CharacterResult(
            created = CHARACTER_CREATED,
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = CharacterOrigin(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = CharacterLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            image = CHARACTER_IMAGE,
            episode = CHARACTER_EPISODES,
            url = CHARACTER_URL,
        )
        val actualCharacterResult : CharacterResult = characterMapper.mapResultResponseForResult(characterResultResponse)
        assertEquals(expectedCharacterResult, actualCharacterResult)
    }

    @Test
    fun modelResultForDbCharacterModel() {

        val characterResult = CharacterResult(
            created = CHARACTER_CREATED,
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = CharacterOrigin(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = CharacterLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            image = CHARACTER_IMAGE,
            episode = CHARACTER_EPISODES,
            url = CHARACTER_URL,
        )

        val expectedDbCharacter = CharacterDbModel(
            created = CHARACTER_CREATED,
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = CHARACTER_ORIGIN_NAME,
            location = CHARACTER_LOCATION_NAME,
            image = CHARACTER_IMAGE,
            url = CHARACTER_URL,
        )
        val actualDbCharacter: CharacterDbModel = characterMapper.mapCharacterResultForCharacterResultDb(characterResult)
        assertEquals(expectedDbCharacter, actualDbCharacter)
    }

    @Test
    fun dbCharacterModelForCharacterResult() {
        val characterDbModel = CharacterDbModel(
            created = CHARACTER_CREATED,
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = CHARACTER_ORIGIN_NAME,
            location = CHARACTER_LOCATION_NAME,
            image = CHARACTER_IMAGE,
            url = CHARACTER_URL,
        )

        val expectedCharacterResult = CharacterResult(
            created = CHARACTER_CREATED,
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = CharacterOrigin(CHARACTER_ORIGIN_NAME, EMPTY_STRING),
            location = CharacterLocation(CHARACTER_LOCATION_NAME, EMPTY_STRING),
            image = CHARACTER_IMAGE,
            episode = emptyList(),
            url = CHARACTER_URL
        )
        val actualCharacterResult: CharacterResult = characterMapper.mapCharacterResultDbForCharacterResult(characterDbModel)
        assertEquals(expectedCharacterResult, actualCharacterResult)
    }

    companion object {
        private const val CHARACTER_ID = 123
        private const val CHARACTER_NAME = "some name"
        private const val CHARACTER_STATUS = "dead"
        private const val CHARACTER_SPECIES = "some species"
        private const val CHARACTER_TYPE = "some type"
        private const val CHARACTER_GENDER = "some gender"
        private const val CHARACTER_ORIGIN_NAME = "some origin name"
        private const val CHARACTER_ORIGIN_URL = "some origin url"
        private const val CHARACTER_LOCATION_NAME = "some location name"
        private const val CHARACTER_LOCATION_URL = "some location url"
        private const val CHARACTER_IMAGE = "some image"
        private val CHARACTER_EPISODES = listOf(
            "https://test.com/api/episode/1",
            "https://test.com/api/episode/2",
            "https://test.com/api/episode/3",
        )
        private const val CHARACTER_URL = "some url"
        private const val CHARACTER_CREATED = "some created"
        private const val EMPTY_STRING = ""
    }
}