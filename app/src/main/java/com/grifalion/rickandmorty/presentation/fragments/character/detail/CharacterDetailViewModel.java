package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.grifalion.rickandmorty.data.api.CharacterApiService;
import com.grifalion.rickandmorty.domain.models.character.CharacterResult;
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult;
import com.grifalion.rickandmorty.domain.usecases.character.GetListCharactersUseCase;
import com.grifalion.rickandmorty.domain.usecases.character.GetListEpisodesIntoCharacterDetailUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CharacterDetailViewModel extends ViewModel {
    public MutableLiveData<CharacterResult> selectedItemCharacter = new MutableLiveData<>();
    public MutableLiveData<List<EpisodeResult>> responseEpisodes = new MutableLiveData<>();
    public List<String> listOfEpisodes = new ArrayList<>();

    public String episodeId;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    GetListEpisodesIntoCharacterDetailUseCase getListEpisodesIntoCharacterDetailUseCase;
    public CharacterApiService apiService = CharacterApiService.Companion.getInstance();
    @Inject
    public CharacterDetailViewModel(GetListEpisodesIntoCharacterDetailUseCase getListEpisodesIntoCharacterDetailUseCase){
        this.getListEpisodesIntoCharacterDetailUseCase = getListEpisodesIntoCharacterDetailUseCase;
    }
    public void onClickItemCharacter(CharacterResult character){
        selectedItemCharacter.setValue(character);
        listOfEpisodes.addAll(character.getEpisode());
    }
    public void setListOfEpisodes(List<EpisodeResult> episode){
        responseEpisodes.setValue(episode);
    }

    public MutableLiveData<CharacterResult> getSelectedItemCharacter(){
        return selectedItemCharacter;
    }

    public void clearListOfEpisodes(){
        listOfEpisodes.clear();
    }

    void fetchData(){
        compositeDisposable.add(getListEpisodesIntoCharacterDetailUseCase.execute(episodeId)
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
