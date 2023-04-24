package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grifalion.rickandmorty.R;
import com.grifalion.rickandmorty.domain.models.character.Character;

import java.util.List;

public class LocationDetailAdapter extends RecyclerView.Adapter<LocationDetailViewHolder> {
    Context context;
    List<Character> listCharacters;

    public LocationDetailAdapter(Context context, List<Character> listCharacters){
        this.context = context;
        this.listCharacters = listCharacters;
    }


    @NonNull
    @Override
    public LocationDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.character_item,parent,false);
       return new LocationDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationDetailViewHolder holder, int position) {
        Glide.with(holder.tvImage)
                .load(listCharacters.get(position).getImage())
                .into(holder.tvImage);
        holder.tvName.setText(listCharacters.get(position).getName());
        holder.tvGender.setText(listCharacters.get(position).getGender());
        holder.tvStatus.setText(listCharacters.get(position).getStatus());
        holder.tvSpecies.setText(listCharacters.get(position).getSpecies());
    }

    @Override
    public int getItemCount() {
        return listCharacters.size();
    }
}
