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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.data.network.ApiService;
import com.grifalion.rickandmorty.data.network.RetrofitInstance;
import com.grifalion.rickandmorty.databinding.CharacterDetailFragmentBinding;
import com.grifalion.rickandmorty.domain.models.character.Character;
import com.grifalion.rickandmorty.domain.models.episode.Episode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CharacterDetailFragment extends Fragment {
    private CharacterDetailFragmentBinding binding;
    private CharacterDetailViewModel viewModelDetail;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView rv;
    ApiService apiService;

    public CharacterDetailFragment(@NotNull CharacterDetailViewModel viewModel){
        this.viewModelDetail = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CharacterDetailFragmentBinding.inflate(inflater);
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
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

        };
        viewModelDetail.getItemListCharacter().observe(getViewLifecycleOwner(),observer);
        viewModelDetail.getEpisodes();
        fetchData();
        viewModelDetail.clearListOfEpisodes();
    }

    private void fetchData() {
        compositeDisposable.add(apiService.getDetailEpisode(viewModelDetail.episodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Episode>>() {
                    @Override
                    public void accept(List<Episode> posts) throws Exception {
                        detailData(posts);
                    }
                }));
    }

    private void detailData(List<Episode> posts) {
        CharacterDetailAdapter adapter = new CharacterDetailAdapter(requireContext(),posts);
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
}
