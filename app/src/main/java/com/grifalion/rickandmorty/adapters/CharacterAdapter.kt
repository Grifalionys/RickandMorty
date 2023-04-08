package com.grifalion.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grifalion.rickandmorty.databinding.ItemCharacterListBinding
import com.grifalion.rickandmorty.models.Character

class CharacterAdapter: RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private var characterList = emptyList<Character>()

    class CharacterViewHolder(val binding: ItemCharacterListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = characterList[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvGender.text = item.gender
        holder.binding.tvStatus.text = item.status
        holder.binding.tvSpecies.text = item.species
        Glide.with(holder.binding.imIconCharacter)
            .load(item.image)
            .into(holder.binding.imIconCharacter)
    }

    fun addCharacter(characters: List<Character>){
        characterList = characters
        notifyDataSetChanged()
    }
}