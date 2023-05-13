package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.mappers.CharacterMapper
import com.grifalion.rickandmorty.domain.models.character.CharacterModel
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val characterDao: CharacterDao,
    val mapper: CharacterMapper
    ): CharacterRepository {

    override suspend fun getCharacter(
        page: Int,
        name: String,
        status: String,
        gender: String,
        species: String): CharacterModel {
        val characterApi = apiService.getCharacter(page,name,gender,status,species)
        val listCharacters = mapper.mapCharacterResponseForCharacter(characterApi)
        characterDao.insertCharacter(mapper.mapListResultResponseForListDb(listCharacters.result))
        return listCharacters
    }

    override suspend fun insertCharacter(list: List<CharacterResult>) {
        characterDao.insertCharacter(mapper.mapListResultResponseForListDb(list))
    }

    override suspend fun getListCharacters(offset: Int,
                                           limit: Int,
                                           name: String,
                                           gender: String,
                                           status: String,
                                           species: String): List<CharacterResult> {
        return characterDao.getCharactersPage(offset, limit, name,gender,status,species).map(mapper::mapCharacterResultDbForCharacterResult)
    }

    override fun getListEpisodesIntoCharacterDetail(id: String): Observable<List<EpisodeResult>> {
        return apiService.getListEpisodesByIdIntoCharacterDetail(id)
    }

}