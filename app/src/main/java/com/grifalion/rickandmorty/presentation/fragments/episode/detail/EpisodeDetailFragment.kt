package com.grifalion.rickandmorty.presentation.fragments.episode.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.EpisodeDetailFragmentBinding
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharacterListViewModel
import com.grifalion.rickandmorty.presentation.fragments.episode.list.EpisodeListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeDetailFragment(val vmEpisode: CharacterDetailViewModel): Fragment() {
    private lateinit var binding: EpisodeDetailFragmentBinding
    private val dataEpisode: EpisodeListViewModel by activityViewModels()
    private val adapter = EpisodeDetailAdapter()
    private lateinit var vmCharacters: CharacterListViewModel
    private lateinit var vmDetailCharacter: CharacterDetailViewModel
    private var name = ""
    private var status = ""
    private var gender = ""
    private var species = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeDetailFragmentBinding.inflate(inflater)
        vmDetailCharacter = ViewModelProvider(this)[CharacterDetailViewModel::class.java]
        vmCharacters = ViewModelProvider(this)[CharacterListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmEpisode.getItemListEpisode().observe(viewLifecycleOwner){
            binding.tvNameEpisodeD.text = it.name
            binding.tvEpisode.text = it.episode
            binding.tvDataEpisode.text = it.air_date
        }
        binding.btnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        binding.rvEpisodeDetail.adapter = adapter
        name = ""
        status = ""
        gender = ""
        species = ""
        lifecycleScope.launch {
            vmCharacters.getCharacters(id,name,status,gender,species)
            vmCharacters.characterFlow.collectLatest(adapter::submitData)
        }

        hideBottomNav()
    }


    private fun hideBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
    }
}