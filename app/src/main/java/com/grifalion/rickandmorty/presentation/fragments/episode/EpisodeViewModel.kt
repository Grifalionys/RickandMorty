package com.grifalion.rickandmorty.presentation.fragments.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import com.grifalion.rickandmorty.data.datasource.EpisodeDataSource
import com.grifalion.rickandmorty.domain.models.character.Character
import com.grifalion.rickandmorty.domain.models.episode.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn

class EpisodeViewModel: ViewModel() {
    var episodeFlow: Flow<PagingData<Episode>> = emptyFlow()
    var dataEpisode = MutableLiveData<Episode>()

    fun getEpisodes(name: String, episode: String){
        episodeFlow = Pager(PagingConfig(pageSize = 1)){
            EpisodeDataSource(name,episode)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}