package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grifalion.rickandmorty.databinding.EpisodeItemBinding

class EpisodeDetailAdapter: RecyclerView.Adapter<EpisodeDetailAdapter.EpisodeDetailViewHolder>() {
    private val listCharacters = ArrayList<com.grifalion.rickandmorty.domain.models.character.Character>()
    class EpisodeDetailViewHolder(var binding: EpisodeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailViewHolder {
        return EpisodeDetailViewHolder(
            EpisodeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = listCharacters.size

    override fun onBindViewHolder(holder: EpisodeDetailViewHolder, position: Int) {
        val item = listCharacters[position]
    }
}