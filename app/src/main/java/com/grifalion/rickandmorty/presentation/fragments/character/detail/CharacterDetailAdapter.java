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
    private Context context;
    private List<Episode> listEpisodes;
    private SelectListener listener;

    public CharacterDetailAdapter(Context context, List<Episode> listEpisodes, SelectListener listener){
        this.context = context;
        this.listEpisodes = listEpisodes;
        this.listener = listener;
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
        Episode item = listEpisodes.get(position);
        holder.tvNameEpisode.setText(listEpisodes.get(position).getName());
        holder.tvNumberEpisode.setText(listEpisodes.get(position).getEpisode());
        holder.tvAirDataEpisode.setText(listEpisodes.get(position).getAir_date());
        holder.itemView.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(item);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listEpisodes.size();
    }

    public interface SelectListener{
        void onItemClicked(Episode episode);
    }
}
