package com.grifalion.rickandmorty.domain.repository


import com.grifalion.rickandmorty.domain.models.character.CharacterModel
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import io.reactivex.Observable

interface CharacterRepository {

    suspend fun getCharacter(page: Int, name: String, status: String, gender: String, species: String): CharacterModel

    suspend fun insertCharacter(list: List<CharacterResult>)

    suspend fun getListCharacters(offset: Int, limit: Int, name: String, gender: String, status: String, species: String): List<CharacterResult>

    fun getListEpisodesIntoCharacterDetail(id: String): Observable<List<EpisodeResult>>
}