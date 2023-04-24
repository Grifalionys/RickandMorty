package com.grifalion.rickandmorty.presentation.fragments.character.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grifalion.rickandmorty.databinding.CharacterItemBinding

import com.grifalion.rickandmorty.domain.models.character.Character

class CharacterListAdapter(private val characterListener: Listener): PagingDataAdapter<Character, CharacterListAdapter.CharacterViewHolder>(
    CharacterComparator
) {

    class CharacterViewHolder(val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.binding.tvName.text = item.name
        holder.binding.tvGender.text = item.gender
        holder.binding.tvStatus.text = item.status
        holder.binding.tvSpecies.text = item.species
        Glide.with(holder.binding.imIconCharacter)
            .load(item.image)
            .into(holder.binding.imIconCharacter)
        holder.itemView.rootView.setOnClickListener {
            characterListener.onClick(item)
        }
    }
    object CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener{
        fun onClick(character: Character)
    }
}