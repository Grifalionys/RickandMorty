package com.grifalion.rickandmorty.ui.fragments.character


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import com.grifalion.rickandmorty.data.network.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn


class CharacterViewModel: ViewModel() {
    var characterFlow: Flow<PagingData<Character>> = emptyFlow()

    fun getCharactersTwo(name: String, status: String, gender: String, species: String){
               characterFlow = Pager(PagingConfig(pageSize = 1)){
                   CharacterDataSource(name,status,gender,species)
               }.flow.cachedIn(viewModelScope)
                   .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
           }
}



