package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.grifalion.rickandmorty.data.api.CharacterApiService;
import com.grifalion.rickandmorty.domain.models.character.CharacterResult;
import com.grifalion.rickandmorty.domain.models.location.Location;
import com.grifalion.rickandmorty.domain.models.location.LocationResult;
import com.grifalion.rickandmorty.domain.usecases.location.GetListLocationsUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LocationDetailViewModel extends ViewModel {
    public MutableLiveData<String> locationName = new MutableLiveData<>();
    public MutableLiveData<LocationResult> selectedItemLocation = new MutableLiveData<>();
    public MutableLiveData<List<CharacterResult>> responseCharacters = new MutableLiveData<List<CharacterResult>>();

    public List<String> listsOfCharacters = new ArrayList<>();
    GetListLocationsUseCase locationUseCase;

    public String characterId;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public CharacterApiService apiService = CharacterApiService.Companion.getInstance();

    @Inject
    public LocationDetailViewModel(GetListLocationsUseCase locationUseCase){
        this.locationUseCase = locationUseCase;
    }
    public void onClickItemCharacter(LocationResult location){
        selectedItemLocation.setValue(location);
        listsOfCharacters.addAll(location.getResidents());
        getCharacters();
        fetchData();
    }
    public void setLocationName(String name){
        locationName.setValue(name);
        fetchDataLocation();
    }

    public void setResponse(List<CharacterResult> character){
        responseCharacters.setValue(character);
    }
    public MutableLiveData<LocationResult> getSelectedItemCharacter(){
        return selectedItemLocation;
    }

    public void setResponseLocation(Location location){
        selectedItemLocation.setValue(location.getResults().get(0));
        onClickItemCharacter(location.getResults().get(0));
    }

    public void clearListOfCharacters(){
        listsOfCharacters.clear();
    }

    public void fetchData(){
        compositeDisposable.add(apiService.getDetailCharacter(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setResponse,throwable -> {}
                ));
    }
    void fetchDataLocation() {
        compositeDisposable.add(apiService.getDetailLocation(locationName.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setResponseLocation, throwable -> {
                }));
    }


    public void getCharacters(){
        String str1;
        String result = "";
        if(!listsOfCharacters.isEmpty()){
            for(String character: listsOfCharacters){
                str1 = character.substring(42);
                result = result + str1 + ",";
            }
        }
        characterId = result;
    }

}
