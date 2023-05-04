package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.api.RetrofitInstance
import com.grifalion.rickandmorty.domain.models.episode.Episode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EpisodeDetailViewModel: ViewModel() {
    val selectedItemLocation = MutableLiveData<Episode>()
    val responseCharacters = MutableLiveData<List<com.grifalion.rickandmorty.domain.models.character.CharacterResult?>?>()
    private val listOfCharacters = mutableListOf<List<String>>()
    private var characterId: String? = null
    private val apiService = RetrofitInstance.getInstance()
    private val compositeDisposable = CompositeDisposable()

    fun onClickItemEpisode(episode: Episode){
        selectedItemLocation.value = episode
        listOfCharacters.add(episode.characters)
    }

    fun fetchData(){
        compositeDisposable.add(apiService.getDetailCharacter(characterId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            { character: List<com.grifalion.rickandmorty.domain.models.character.CharacterResult?>? ->
                this.responseCharacters.value = character
            }) {throwable: Throwable? -> })
    }

    fun getCharacters(){
        var str1: String
        var result = ""
        if(listOfCharacters.isNotEmpty()){
            for(episode in listOfCharacters[0]){
                str1 = episode.substring(42)
                result = "$result+$str1,"
            }
        }
        characterId = result
    }

    fun clearListCharacters(){
        listOfCharacters.clear()
    }
}