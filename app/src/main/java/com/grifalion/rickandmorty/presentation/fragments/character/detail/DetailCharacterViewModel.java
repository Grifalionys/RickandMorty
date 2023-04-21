package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grifalion.rickandmorty.domain.models.character.Character;

import java.util.ArrayList;
import java.util.List;

public class DetailCharacterViewModel extends ViewModel {
    public MutableLiveData<Character> itemListCharacter = new MutableLiveData<>();
    public List<String> listOfEpisodes = new ArrayList<>();
    public String episodeId;

    public void onClickItemCharacter(Character character){
        itemListCharacter.setValue(character);
        listOfEpisodes.addAll(character.getEpisode());
    }


    public MutableLiveData<Character> getItemListCharacter(){
        return itemListCharacter;
    }
}
