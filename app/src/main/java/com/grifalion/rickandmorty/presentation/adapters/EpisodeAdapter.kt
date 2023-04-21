package com.grifalion.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grifalion.rickandmorty.databinding.ItemEpisodeListBinding
import com.grifalion.rickandmorty.domain.models.episode.Episode

class EpisodeAdapter(val listenerEpisode: ListenerEpisode): PagingDataAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(EpisodeComparator) {

    class EpisodeViewHolder(val binding: ItemEpisodeListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.binding.tvNameEpisode.text = item.name
        holder.binding.tvNumberEpisode.text = item.episode
        holder.binding.tvData.text = item.air_date
        holder.itemView.rootView.setOnClickListener {
            listenerEpisode.onClick(item)
        }
    }

    object EpisodeComparator : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
           return oldItem == newItem
        }
    }
    interface ListenerEpisode{
        fun onClick(episode: Episode)
    }
}