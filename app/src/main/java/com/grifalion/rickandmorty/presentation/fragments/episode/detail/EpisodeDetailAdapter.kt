package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grifalion.rickandmorty.databinding.CharacterItemBinding
import com.grifalion.rickandmorty.domain.models.character.CharacterResult


class EpisodeDetailAdapter: PagingDataAdapter<CharacterResult, EpisodeDetailAdapter.EpisodeDetailViewHolder>(EpisodeDetailComparator) {
    class EpisodeDetailViewHolder(var binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailViewHolder {
        return EpisodeDetailViewHolder(
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: EpisodeDetailViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.binding.tvName.text = item.name
        holder.binding.tvGender.text = item.gender
        holder.binding.tvStatus.text = item.status
        holder.binding.tvSpecies.text = item.species
        Glide.with(holder.binding.imIconCharacter)
            .load(item.image)
            .into(holder.binding.imIconCharacter)
    }
    object EpisodeDetailComparator : DiffUtil.ItemCallback<CharacterResult>() {
        override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
            return oldItem == newItem
        }

    }
}


