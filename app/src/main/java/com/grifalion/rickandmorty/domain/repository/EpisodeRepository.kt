package com.grifalion.rickandmorty.domain.repository

import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import io.reactivex.Observable

interface EpisodeRepository {

    suspend fun getEpisode(page: Int, name: String, episode: String): Episode

    suspend fun insertEpisode(list: List<EpisodeResult>)

    fun getListEpisodesDb(): List<EpisodeResult>

    fun getListCharactersIntoEpisodeDetail(id: String): Observable<List<CharacterResult>>

}