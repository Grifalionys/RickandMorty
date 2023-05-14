package com.grifalion.rickandmorty.data.mappers

<<<<<<< HEAD
=======
import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterLocationResponse
import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterOriginResponse
import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterResultResponse
>>>>>>> origin/master
import com.grifalion.rickandmorty.data.db.entity.character.CharacterDbModel
import com.grifalion.rickandmorty.domain.models.character.CharacterLocation
import com.grifalion.rickandmorty.domain.models.character.CharacterOrigin
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
<<<<<<< HEAD
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMapperTest {
    private val characterMapper = CharacterMapper()

    @Test
    fun modelToDbCharacter() {

        val characterResult = CharacterResult(
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
            created = CHARACTER_CREATED,
        )

        val expectedDbCharacter = CharacterDbModel(
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
            created = CHARACTER_CREATED,
        )

        val actualDbCharacter: CharacterDbModel =
            characterMapper.mapCharacterResultForCharacterResultDb(characterResult)

        assertEquals(expectedDbCharacter, actualDbCharacter)

    }

=======
import org.junit.Assert.*
import org.junit.Test

class CharacterMapperTest {
    @Test
    fun modelToDbCharacter()  {
        val apiCharacter = CharacterResultResponse(
            created = CHARACTER_CREATED,
            episode = CHARACTER_EPISODES,
            gender = CHARACTER_GENDER,
            id = CHARACTER_ID,
            image = CHARACTER_IMAGE,
            location = CharacterLocationResponse(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            origin = CharacterOriginResponse(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            name = CHARACTER_NAME,
            species = CHARACTER_SPECIES,
            status = CHARACTER_STATUS,
            type = CHARACTER_TYPE,
            url = CHARACTER_URL
        )

        val expectedDbCharacter = CharacterDbModel(
            created = CHARACTER_CREATED,
            gender = CHARACTER_GENDER,
            id = CHARACTER_ID,
            image = CHARACTER_IMAGE,
            name = CHARACTER_NAME,
            species = CHARACTER_SPECIES,
            status = CHARACTER_STATUS,
            type = CHARACTER_TYPE,
            url = CHARACTER_URL,
            location = CHARACTER_LOCATION_NAME,
            origin = CHARACTER_ORIGIN_NAME
        )
        assertEquals(expectedDbCharacter,apiCharacter)
    }

    @Test
    fun example1(){
        assertEquals(4,2+2)
    }

        @Test
        fun dbToModelCharacter(){
            val dbCharacter = CharacterDbModel(
                created = CHARACTER_CREATED,
                gender = CHARACTER_GENDER,
                id = CHARACTER_ID,
                image = CHARACTER_IMAGE,
                name = CHARACTER_NAME,
                species = CHARACTER_SPECIES,
                status = CHARACTER_STATUS,
                type = CHARACTER_TYPE,
                url = CHARACTER_URL,
                location = "",
                origin = ""
            )
            val expectedModelCharacter = CharacterResult(
                created = CHARACTER_CREATED,
                episode = CHARACTER_EPISODES,
                gender = CHARACTER_GENDER,
                id = CHARACTER_ID,
                image = CHARACTER_IMAGE,
                name = CHARACTER_NAME,
                species = CHARACTER_SPECIES,
                status = CHARACTER_STATUS,
                type = CHARACTER_TYPE,
                url = CHARACTER_URL,
                location = CharacterLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
                origin = CharacterOrigin(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL)
            )
    }
>>>>>>> origin/master

    companion object {
        private const val CHARACTER_ID = 123
        private const val CHARACTER_NAME = "some name"
        private const val CHARACTER_STATUS = "dead"
<<<<<<< HEAD
=======
        private const val CHARACTER_STATUS_ALIVE = false
>>>>>>> origin/master
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
        private val CHARACTER_EPISODE_IDS = listOf(1, 2, 3)
        private const val CHARACTER_URL = "some url"
        private const val CHARACTER_CREATED = "some created"
<<<<<<< HEAD
=======
        private const val CHARACTER_IS_KILLED_BY_USER = true
>>>>>>> origin/master
    }
}