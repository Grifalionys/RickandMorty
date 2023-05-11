package com.grifalion.rickandmorty.domain.usecases.episode

import android.app.Application
import com.grifalion.rickandmorty.data.repository.CharacterRepositoryImpl
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.repository.EpisodeRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetListCharactersIntoEpisodeUseCase @Inject constructor(
    private val repository: EpisodeRepository,
    private val application: Application
) {
    fun execute(id: String): Observable<List<CharacterResult>> {
        return repository.getListCharactersIntoEpisodeDetail(id)
    }
}