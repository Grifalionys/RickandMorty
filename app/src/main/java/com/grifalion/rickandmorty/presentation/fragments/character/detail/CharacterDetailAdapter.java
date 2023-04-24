package com.grifalion.rickandmorty.presentation.fragments.character.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.domain.models.episode.Episode;

import java.util.List;

public class CharacterDetailAdapter extends RecyclerView.Adapter<CharacterDetailViewHolder> {
    Context context;
    List<Episode> listEpisodes;

    public CharacterDetailAdapter(Context context, List<Episode> listEpisodes){
        this.context = context;
        this.listEpisodes = listEpisodes;
    }



    @NonNull
    @Override
    public CharacterDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_item,parent,false);
        return new CharacterDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterDetailViewHolder holder, int position) {
        holder.tvNameEpisode.setText(listEpisodes.get(position).getName());
        holder.tvNumberEpisode.setText(listEpisodes.get(position).getEpisode());
        holder.tvAirDataEpisode.setText(listEpisodes.get(position).getAir_date());
    }

    @Override
    public int getItemCount() {
        return listEpisodes.size();
    }
}
