package com.grifalion.rickandmorty.presentation.fragments.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.domain.models.character.Character

class MainViewModel: ViewModel() {
    val dataCharacter = MutableLiveData<Character>()

}