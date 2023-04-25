package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.repository.Repository
import com.grifalion.rickandmorty.domain.models.episode.Episode
import retrofit2.Response

class EpisodeDetailViewModel: ViewModel() {
    val listCharacters = ArrayList<String>()
    var charactersIds: String = ""


    fun getCharacters(){
        var str1 = ""
        var result = ""
        if(!listCharacters.isEmpty()){
            for(characters in listCharacters){
                str1 = characters.substring(40)
                result = result + str1 + ","
            }
        }
        charactersIds = result
    }

}