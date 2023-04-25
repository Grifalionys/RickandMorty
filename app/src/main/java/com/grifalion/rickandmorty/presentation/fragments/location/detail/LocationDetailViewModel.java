package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grifalion.rickandmorty.domain.models.character.Character;
import com.grifalion.rickandmorty.domain.models.location.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationDetailViewModel extends ViewModel {

    public MutableLiveData<Location> itemListLocation = new MutableLiveData<>();
    public MutableLiveData<Character> itemListCharacter = new MutableLiveData<>();

    public List<String> listsOfHeroes = new ArrayList<>();

    public String characterId;

    public void onClickItemLocation(Location location){
        itemListLocation.setValue(location);
        listsOfHeroes.addAll(location.getResidents());
    }

    public void onClickItemCharacter(Character character){
        itemListCharacter.setValue(character);
    }
    public MutableLiveData<Character> getItemListCharacters(){
        return itemListCharacter;
    }

    public MutableLiveData<Location> getItemListLocations(){
        return itemListLocation;
    }

    public void clearListOfCharacters(){
        listsOfHeroes.clear();
    }

    public void getCharacters(){
        String str1;
        String result = "";
        if(!listsOfHeroes.isEmpty()){
            for(String character: listsOfHeroes){
                str1 = character.substring(42);
                result = result + str1 + ",";
            }
        }
        characterId = result;
    }
}
