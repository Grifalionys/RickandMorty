package com.grifalion.rickandmorty.presentation.fragments.character

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grifalion.rickandmorty.models.CharacterList
import com.grifalion.rickandmorty.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class CharacterViewModel(application: Application): AndroidViewModel(application) {
    val REPOSITORY = Repository()
    var charactersList: MutableLiveData<Response<CharacterList>> = MutableLiveData()

    fun getCharacters(page: Int){
        viewModelScope.launch {
            charactersList.postValue(REPOSITORY.getCharacter(page))
        }
    }
}