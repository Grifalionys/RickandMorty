package com.grifalion.rickandmorty.presentation.fragments.character.detail;

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

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.data.api.ApiService;
import com.grifalion.rickandmorty.data.api.RetrofitInstance;
import com.grifalion.rickandmorty.databinding.CharacterDetailFragmentBinding;
import com.grifalion.rickandmorty.domain.models.character.Character;
import com.grifalion.rickandmorty.domain.models.episode.Episode;
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment;
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailViewModel;
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailFragment;
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CharacterDetailFragment extends Fragment implements CharacterDetailAdapter.SelectListener {
    private CharacterDetailFragmentBinding binding;
    private CharacterDetailViewModel characterDetailViewModel;
    private EpisodeDetailViewModel episodeDetailViewModel;
    private LocationDetailViewModel locationDetailViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView rv;
    private ApiService apiService;

    public CharacterDetailFragment(@NotNull CharacterDetailViewModel characterDetailViewModel){
        this.characterDetailViewModel = characterDetailViewModel;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CharacterDetailFragmentBinding.inflate(inflater);
        episodeDetailViewModel = new ViewModelProvider(this).get(EpisodeDetailViewModel.class);
        locationDetailViewModel = new ViewModelProvider(this).get(LocationDetailViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBottomNav();
        apiService = RetrofitInstance.INSTANCE.getCharacterApi();
        rv = binding.rvDetailCharacter;
        rv.setHasFixedSize(true);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        final Observer<Character> observer = character -> {
            assert character != null;
            Glide.with(requireContext())
                    .load(character.getImage())
                    .into(binding.imIconDetail);
            binding.tvGenderDetail.setText(character.getGender());
            binding.tvStatusDeatail.setText(character.getStatus());
            binding.tvSpeciesDetail.setText(character.getSpecies());
            binding.tvLocationDetail.setText(character.getLocation().getName());
            binding.tvOriginDetail.setText(character.getOrigin().getName());
            binding.tvNameDetail.setText(character.getName());
            binding.tvOriginDetail.setOnClickListener(it -> {
                locationDetailViewModel.setLocationName(character.getOrigin().getName());
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.containerFragment,new LocationDetailFragment(locationDetailViewModel))
                            .addToBackStack(null)
                            .commit();
            });
            binding.tvLocationDetail.setOnClickListener(it -> {
                locationDetailViewModel.setLocationName(character.getLocation().getName());
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.containerFragment,new LocationDetailFragment(locationDetailViewModel))
                        .addToBackStack(null)
                        .commit();
            });

        };
        characterDetailViewModel.getSelectedItemCharacter().observe(getViewLifecycleOwner(),observer);
        characterDetailViewModel.getEpisodes();
        characterDetailViewModel.clearListOfEpisodes();
        characterDetailViewModel.fetchData();
        detailData();
    }

    private void detailData() {
        final Observer<List<Episode>> observer = listOfEpisodes -> {
            CharacterDetailAdapter adapter = new CharacterDetailAdapter(requireContext(),listOfEpisodes,this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
            characterDetailViewModel.responseEpisodes.observe(getViewLifecycleOwner(),observer);
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
    public void onItemClicked(Episode episode) {
        episodeDetailViewModel.onClickItemEpisode(episode);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.containerFragment, new EpisodeDetailFragment(episodeDetailViewModel))
                .addToBackStack(null)
                .commit();
    }
}
