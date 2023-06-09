package com.grifalion.rickandmorty.presentation.fragments.location.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grifalion.rickandmorty.databinding.LocationItemBinding
import com.grifalion.rickandmorty.domain.models.location.Location
import com.grifalion.rickandmorty.domain.models.location.LocationResult

class LocationListAdapter(private val locationListener: Listener): PagingDataAdapter<LocationResult, LocationListAdapter.LocationViewHolder>(
    LocationComparator
) {

    class LocationViewHolder(val binding: LocationItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.binding.tvNameLocation.text = item.name
        holder.binding.tvDimension.text = item.dimension
        holder.binding.tvTypeLocation.text = item.type
        holder.itemView.rootView.setOnClickListener {
            locationListener.onClick(item)
        }
    }


    object LocationComparator : DiffUtil.ItemCallback<LocationResult>() {
        override fun areItemsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
            return oldItem == newItem
        }

    }
    interface Listener{
        fun onClick(location: LocationResult)
    }

}
