package com.grifalion.rickandmorty.ui.fragments.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.network.models.Character
import com.grifalion.rickandmorty.data.network.CharacterApiService

class MainViewModel: ViewModel() {
    val dataCharacter = MutableLiveData<Character>()

}