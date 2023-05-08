package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.data.api.LocationApiService;
import com.grifalion.rickandmorty.databinding.LocationDetailFragmentBinding;
import com.grifalion.rickandmorty.domain.models.character.CharacterResult;
import com.grifalion.rickandmorty.domain.models.location.LocationResult;
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment;
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;


public class LocationDetailFragment extends Fragment implements LocationDetailAdapter.SelectListener {

    private LocationDetailFragmentBinding binding;
    private LocationDetailViewModel locationDetailViewModel;
    private CharacterDetailViewModel characterDetailViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView rv;
    private LocationApiService apiService;

    public LocationDetailFragment(@NotNull LocationDetailViewModel locationDetailViewModel){
        this.locationDetailViewModel = locationDetailViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LocationDetailFragmentBinding.inflate(inflater);
        characterDetailViewModel = new ViewModelProvider(this).get(CharacterDetailViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBottomNav();
        apiService = LocationApiService.Companion.getLocationRetrofit();
        rv = binding.rvDetailLocation;
        rv.setHasFixedSize(true);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        final Observer<LocationResult> observer = location -> {
            assert location != null;
            binding.tvNameLocationD.setText(location.getName());
            binding.tvDimensionD.setText(location.getDimension());
            binding.tvTypeLocationD.setText(location.getType());

        };
        locationDetailViewModel.getSelectedItemCharacter().observe(getViewLifecycleOwner(),observer);
        detailData();
        locationDetailViewModel.clearListOfCharacters();
    }


    private void detailData() {
        final Observer<List<CharacterResult>> observer = listOfCharacter ->{
            LocationDetailAdapter adapter = new LocationDetailAdapter(requireContext(),listOfCharacter,this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
        locationDetailViewModel.responseCharacters.observe(getViewLifecycleOwner(),observer);
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
    public void onItemClicked(CharacterResult character) {
        characterDetailViewModel.onClickItemCharacter(character);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.containerFragment, new CharacterDetailFragment(characterDetailViewModel))
                .addToBackStack(null)
                .commit();
    }
}
