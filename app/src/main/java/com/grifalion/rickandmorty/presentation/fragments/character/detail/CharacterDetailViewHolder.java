package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grifalion.rickandmorty.R;

public class CharacterDetailViewHolder extends RecyclerView.ViewHolder {
    TextView tvNameEpisode, tvNumberEpisode, tvAirDataEpisode;

    public CharacterDetailViewHolder(View view){
        super(view);
        tvNameEpisode = view.findViewById(R.id.tvNameEpisode);
        tvNumberEpisode = view.findViewById(R.id.tvNumberEpisode);
        tvAirDataEpisode = view.findViewById(R.id.tvData);
    }
}
