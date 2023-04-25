package com.grifalion.rickandmorty.presentation.fragments.character.list


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


class CharacterListViewModel: ViewModel() {
    var characterFlow: Flow<PagingData<Character>> = emptyFlow()

    fun getCharacters(id: Int,name: String, status: String, gender: String, species: String){
               characterFlow = Pager(PagingConfig(pageSize = 1)){
                   CharacterDataSource(id,name,status,gender,species)
               }.flow.cachedIn(viewModelScope)
                   .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
           }

}



