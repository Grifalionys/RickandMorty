package com.grifalion.rickandmorty.domain.usecases.episode

import android.app.Application
import com.grifalion.rickandmorty.data.datasource.EpisodeDataSource
import com.grifalion.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.grifalion.rickandmorty.domain.repository.EpisodeRepository
import javax.inject.Inject

class GetEpisodeUseCase @Inject constructor(
    private val repository: EpisodeRepositoryImpl,
    private val application: Application
) {
    fun getEpisodes(name: String, episode: String): EpisodeDataSource {
        return EpisodeDataSource(repository, application,name,episode)
    }
}