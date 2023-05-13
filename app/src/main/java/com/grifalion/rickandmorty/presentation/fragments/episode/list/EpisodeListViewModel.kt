package com.grifalion.rickandmorty.presentation.fragments.episode.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.usecases.episode.GetListEpisodesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
    private val getListEpisodesUseCase: GetListEpisodesUseCase
): ViewModel() {
    var episodeFlow: Flow<PagingData<EpisodeResult>> = emptyFlow()

    fun getEpisodes(name: String, episode: String) {
        episodeFlow = Pager(PagingConfig(pageSize = 10, enablePlaceholders = false, initialLoadSize = 10)){
            getListEpisodesUseCase.execute(name,episode)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}

