package com.grifalion.rickandmorty.presentation.fragments.character.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.usecases.character.GetListCharactersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class CharacterListViewModel @Inject constructor(
    private val getListCharactersUseCase: GetListCharactersUseCase
): ViewModel() {
    var characterFlow: Flow<PagingData<CharacterResult>> = emptyFlow()


    fun getCharacters(name: String, status: String, gender: String, species: String) {
        characterFlow = Pager(PagingConfig(pageSize = 1)) {
            getListCharactersUseCase.execute(name, status, gender, species)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

}




