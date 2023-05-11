package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.app.App
import com.grifalion.rickandmorty.databinding.EpisodeDetailFragmentBinding
import com.grifalion.rickandmorty.di.ViewModelFactory
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailAdapter
import javax.inject.Inject

class EpisodeDetailFragment: Fragment(), LocationDetailAdapter.SelectListener {
    private lateinit var binding: EpisodeDetailFragmentBinding
    lateinit var adapter: LocationDetailAdapter
    private val vmDetailCharacter: CharacterDetailViewModel by activityViewModels()
    private lateinit var episodeViewModel: EpisodeDetailViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy{
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeDetailFragmentBinding.inflate(inflater)
        episodeViewModel = ViewModelProvider(requireActivity(),viewModelFactory)[EpisodeDetailViewModel::class.java]
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
            ?.replace(R.id.containerFragment, CharacterDetailFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onStop() {
        super.onStop()
        episodeViewModel.clearListCharacters()
    }
}