package com.grifalion.rickandmorty.presentation.fragments.character


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import com.grifalion.rickandmorty.domain.models.character.Character
import com.grifalion.rickandmorty.domain.models.episode.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn


class CharacterViewModel: ViewModel() {
    var characterFlow: Flow<PagingData<Character>> = emptyFlow()
    var episodeFlow: Flow<PagingData<Episode>> = emptyFlow()

    fun getCharacters(id: Int,name: String, status: String, gender: String, species: String){
               characterFlow = Pager(PagingConfig(pageSize = 1)){
                   CharacterDataSource(id,name,status,gender,species)
               }.flow.cachedIn(viewModelScope)
                   .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
           }

}



