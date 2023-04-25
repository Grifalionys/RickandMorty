package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.data.network.ApiService;
import com.grifalion.rickandmorty.data.network.RetrofitInstance;
import com.grifalion.rickandmorty.databinding.LocationDetailFragmentBinding;
import com.grifalion.rickandmorty.domain.models.character.Character;
import com.grifalion.rickandmorty.domain.models.location.Location;
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment;
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel;
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocationDetailFragment extends Fragment implements LocationDetailAdapter.SelectListener {

    private LocationDetailFragmentBinding binding;
    private LocationDetailViewModel viewModelDetails;


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView rv;
    ApiService apiService;

    public LocationDetailFragment(@NotNull LocationDetailViewModel viewModel){
        this.viewModelDetails = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LocationDetailFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBottomNav();
        apiService = RetrofitInstance.INSTANCE.getCharacterApi();
        rv = binding.rvDetailLocation;
        rv.setHasFixedSize(true);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        final Observer<Location> observer = location -> {
            assert location != null;
            binding.tvNameLocationD.setText(location.getName());
            binding.tvDimensionD.setText(location.getDimension());
            binding.tvTypeLocationD.setText(location.getType());

        };
        viewModelDetails.getItemListLocations().observe(getViewLifecycleOwner(),observer);
        viewModelDetails.getCharacters();
        fetchData();
        viewModelDetails.clearListOfCharacters();
    }

    private void fetchData() {
        compositeDisposable.add(apiService.getDetailCharacter(viewModelDetails.characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::detailData, throwable -> Log.d("tag",throwable.toString())));
    }

    private void detailData(List<Character> post) {
        LocationDetailAdapter adapter = new LocationDetailAdapter(requireContext(),post,this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    public void hideBottomNav(){
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(Character character) {
        viewModelDetails.onClickItemCharacter(character);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.containerFragment, new CharacterDetailFragment(viewModelDetails))
                .addToBackStack("location_detail")
                .commit();
    }
}
