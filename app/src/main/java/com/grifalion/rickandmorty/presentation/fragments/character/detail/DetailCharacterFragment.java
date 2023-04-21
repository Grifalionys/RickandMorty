package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.databinding.CharacterDetailFragmentBinding;
import com.grifalion.rickandmorty.domain.models.character.Character;

public class DetailCharacterFragment extends Fragment {
    private CharacterDetailFragmentBinding binding;
    private DetailCharacterViewModel viewModel;

    public DetailCharacterFragment(@NonNull DetailCharacterViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CharacterDetailFragmentBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(DetailCharacterViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBottomNav();
        TextView tvGenderDetail =  binding.tvGenderDetail;
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
            tvGenderDetail.setText(character.getGender());

        };
        viewModel.getItemListCharacter().observe(getViewLifecycleOwner(),observer);
    }

    public void hideBottomNav(){
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.GONE);
    }
}
