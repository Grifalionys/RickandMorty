package com.grifalion.rickandmorty.presentation.fragments.episode.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grifalion.rickandmorty.databinding.EpisodeItemBinding
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult

class EpisodeListAdapter(val listenerEpisode: ListenerEpisode): PagingDataAdapter<EpisodeResult, EpisodeListAdapter.EpisodeViewHolder>(
    EpisodeComparator
) {

    class EpisodeViewHolder(val binding: EpisodeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            EpisodeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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

    object EpisodeComparator : DiffUtil.ItemCallback<EpisodeResult>() {
        override fun areItemsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
           return oldItem == newItem
        }
    }
    interface ListenerEpisode{
        fun onClick(episode: EpisodeResult)
    }
}