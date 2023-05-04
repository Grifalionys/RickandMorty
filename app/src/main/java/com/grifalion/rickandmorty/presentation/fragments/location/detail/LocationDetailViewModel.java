package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grifalion.rickandmorty.data.api.ApiService;
import com.grifalion.rickandmorty.data.api.RetrofitInstance;
import com.grifalion.rickandmorty.data.api.repsonse.location.LocationResponse;
import com.grifalion.rickandmorty.domain.models.character.CharacterResult;
import com.grifalion.rickandmorty.domain.models.location.Location;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LocationDetailViewModel extends ViewModel {
    public MutableLiveData<String> locationName = new MutableLiveData<>();
    public MutableLiveData<Location> selectedItemLocation = new MutableLiveData<>();
    public MutableLiveData<List<CharacterResult>> responseCharacters = new MutableLiveData<List<CharacterResult>>();

    public List<String> listsOfCharacters = new ArrayList<>();

    public String characterId;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ApiService apiService = RetrofitInstance.INSTANCE.getCharacterApi();

    public void onClickItemCharacter(Location location){
        selectedItemLocation.setValue(location);
        listsOfCharacters.addAll(location.getResidents());
    }
    public void setLocationName(String name){
        locationName.setValue(name);
        fetchDataLocation();
    }

    public void setResponse(List<CharacterResult> character){
        responseCharacters.setValue(character);
    }
    public MutableLiveData<Location> getSelectedItemCharacter(){
        return selectedItemLocation;
    }

    public void setResponseLocation(LocationResponse locationResponse){
        selectedItemLocation.setValue(locationResponse.getResults().get(0));
        onClickItemCharacter(locationResponse.getResults().get(0));
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
