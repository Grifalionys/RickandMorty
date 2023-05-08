package com.grifalion.rickandmorty.presentation.fragments.episode.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grifalion.rickandmorty.data.datasource.EpisodeDataSource
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.usecases.episode.GetEpisodeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase
): ViewModel() {
    var episodeFlow: Flow<PagingData<EpisodeResult>> = emptyFlow()

    fun getEpisodes(name: String, episode: String) {
        episodeFlow = Pager(PagingConfig(pageSize = 1)){
            getEpisodeUseCase.getEpisodes(name,episode)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}

