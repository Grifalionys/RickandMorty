package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.EpisodeDetailFragmentBinding
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailAdapter

class EpisodeDetailFragment(private val episodeViewModel: EpisodeDetailViewModel): Fragment(), LocationDetailAdapter.SelectListener {
    private lateinit var binding: EpisodeDetailFragmentBinding
    lateinit var adapter: LocationDetailAdapter
    private val vmDetailCharacter: CharacterDetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()
        binding.btnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        episodeViewModel.selectedItemLocation.observe(viewLifecycleOwner){
            binding.tvNameEpisodeD.text = it.name
            binding.tvEpisode.text = it.episode
            binding.tvDataEpisode.text = it.air_date
        }
        episodeViewModel.getCharacters()
        episodeViewModel.fetchData()
        episodeViewModel.responseCharacters.observe(viewLifecycleOwner){
            adapter = LocationDetailAdapter(requireContext(),it,this)
            binding.rvEpisodeDetail.adapter = adapter

        }
    }



    private fun hideBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onItemClicked(character: CharacterResult?) {
        vmDetailCharacter.onClickItemCharacter(character)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.containerFragment, CharacterDetailFragment(vmDetailCharacter))
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onStop() {
        super.onStop()
        episodeViewModel.clearListCharacters()
    }
}