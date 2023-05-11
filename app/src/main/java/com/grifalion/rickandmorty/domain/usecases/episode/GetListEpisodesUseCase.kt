package com.grifalion.rickandmorty.domain.usecases.episode

import android.app.Application
import com.grifalion.rickandmorty.data.datasource.EpisodeDataSource
import com.grifalion.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import io.reactivex.Observable
import javax.inject.Inject

class GetListEpisodesUseCase @Inject constructor(
    private val repository: EpisodeRepositoryImpl,
    private val application: Application
) {
    fun execute(name: String, episode: String): EpisodeDataSource {
        return EpisodeDataSource(repository, application,name,episode)
    }
}