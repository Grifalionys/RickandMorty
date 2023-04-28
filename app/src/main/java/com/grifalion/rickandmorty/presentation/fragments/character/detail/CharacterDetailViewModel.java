package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grifalion.rickandmorty.data.api.ApiService;
import com.grifalion.rickandmorty.data.api.RetrofitInstance;
import com.grifalion.rickandmorty.domain.models.character.Character;
import com.grifalion.rickandmorty.domain.models.episode.Episode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CharacterDetailViewModel extends ViewModel {
    public MutableLiveData<Character> selectedItemCharacter = new MutableLiveData<>();
    public MutableLiveData<List<Episode>> responseEpisodes = new MutableLiveData<List<Episode>>();
    public List<String> listOfEpisodes = new ArrayList<>();

    public String episodeId;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ApiService apiService = RetrofitInstance.INSTANCE.getCharacterApi();

    public void onClickItemCharacter(Character character){
        selectedItemCharacter.setValue(character);
        listOfEpisodes.addAll(character.getEpisode());
    }
    public void setListOfEpisodes(List<Episode> episode){
        responseEpisodes.setValue(episode);
    }

    public MutableLiveData<Character> getSelectedItemCharacter(){
        return selectedItemCharacter;
    }

    public void clearListOfEpisodes(){
        listOfEpisodes.clear();
    }

    void fetchData(){
        compositeDisposable.add(apiService.getDetailEpisode(episodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setListOfEpisodes,throwable -> {}));
    }

    public void getEpisodes(){
        String str1;
        String result = "";
        if(!listOfEpisodes.isEmpty()) {
            for (String episode : listOfEpisodes) {
                str1 = episode.substring(40);
                result = result + str1 + ",";
            }
        }
        episodeId = result;
    }

}
