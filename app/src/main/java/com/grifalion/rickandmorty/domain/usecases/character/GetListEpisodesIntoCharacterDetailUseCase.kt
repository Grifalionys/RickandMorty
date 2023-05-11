package com.grifalion.rickandmorty.domain.usecases.character

import android.app.Application
import com.grifalion.rickandmorty.data.repository.CharacterRepositoryImpl
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import io.reactivex.Observable
import javax.inject.Inject

class GetListEpisodesIntoCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val application: Application
) {
    fun execute(id: String): Observable<List<EpisodeResult>> {
        return repository.getListEpisodesIntoCharacterDetail(id)
    }
}