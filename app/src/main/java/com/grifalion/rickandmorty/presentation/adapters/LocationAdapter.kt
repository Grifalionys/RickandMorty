package com.grifalion.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grifalion.rickandmorty.databinding.ItemLocationListBinding
import com.grifalion.rickandmorty.domain.models.location.Location

class LocationAdapter: PagingDataAdapter<Location,LocationAdapter.LocationViewHolder>(LocationComparator) {

    class LocationViewHolder(val binding: ItemLocationListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            ItemLocationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.binding.tvNameLocation.text = item.name
        holder.binding.tvDimension.text = item.dimension
        holder.binding.tvTypeLocation.text = item.type
    }


    object LocationComparator : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

    }
}
