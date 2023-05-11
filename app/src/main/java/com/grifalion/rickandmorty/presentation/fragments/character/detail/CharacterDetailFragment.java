package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.astonproject.app.di.AppComponent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.app.App;
import com.grifalion.rickandmorty.data.api.CharacterApiService;
import com.grifalion.rickandmorty.databinding.CharacterDetailFragmentBinding;
import com.grifalion.rickandmorty.di.ViewModelFactory;
import com.grifalion.rickandmorty.domain.models.character.CharacterResult;
import com.grifalion.rickandmorty.domain.models.episode.Episode;
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult;
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment;
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailViewModel;
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailFragment;
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CharacterDetailFragment extends Fragment implements CharacterDetailAdapter.SelectListener {
    private CharacterDetailFragmentBinding binding;
    private CharacterDetailViewModel characterDetailViewModel;
    private EpisodeDetailViewModel episodeDetailViewModel;
    private LocationDetailViewModel locationDetailViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView rv;
    private CharacterApiService apiService;
    private AppComponent appComponent;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        App application = (App) requireActivity().getApplication();
        appComponent = application.getComponent();
        appComponent.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CharacterDetailFragmentBinding.inflate(inflater);
        characterDetailViewModel = new ViewModelProvider(requireActivity(),viewModelFactory).get(CharacterDetailViewModel.class);
        episodeDetailViewModel = new ViewModelProvider(requireActivity(),viewModelFactory).get(EpisodeDetailViewModel.class);
        locationDetailViewModel = new ViewModelProvider(requireActivity(),viewModelFactory).get(LocationDetailViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBottomNav();
        apiService = CharacterApiService.Companion.getCharacterRetrofit();
        rv = binding.rvDetailCharacter;
        rv.setHasFixedSize(true);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        final Observer<CharacterResult> observer = character -> {
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
            binding.tvCreated.setText(character.getCreated());
            if(binding.tvStatusDeatail.getText().toString().equals("Alive")){
                binding.tvStatusDeatail.setBackground(ContextCompat.getDrawable(requireActivity(),R.drawable.view_alive));
            } else if(binding.tvStatusDeatail.getText().toString().equals("Dead")){
                binding.tvStatusDeatail.setBackground(ContextCompat.getDrawable(requireActivity(),R.drawable.view_dead));
            } else if(binding.tvStatusDeatail.getText().toString().equals("unknown")){
                binding.tvStatusDeatail.setBackground(ContextCompat.getDrawable(requireActivity(),R.drawable.view_unknown));
            }
            binding.tvOriginDetail.setOnClickListener(it -> {
                locationDetailViewModel.setLocationName(character.getOrigin().getName());
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.containerFragment,new LocationDetailFragment())
                            .addToBackStack(null)
                            .commit();
            });
            binding.tvLocationDetail.setOnClickListener(it -> {
                locationDetailViewModel.setLocationName(character.getLocation().getName());
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.containerFragment,new LocationDetailFragment())
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
        final Observer<List<EpisodeResult>> observer = listOfEpisodes -> {
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
    public void onItemClicked(EpisodeResult episode) {
        episodeDetailViewModel.onClickItemEpisode(episode);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.containerFragment, new EpisodeDetailFragment())
                .addToBackStack(null)
                .commit();
    }
}
