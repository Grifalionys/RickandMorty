package com.grifalion.rickandmorty.presentation.fragments.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.EpisodeDetailFragmentBinding
import com.grifalion.rickandmorty.presentation.fragments.character.CharacterViewModel
import com.grifalion.rickandmorty.presentation.fragments.character.MainViewModel

class DetailEpisodeFragment: Fragment() {
    private lateinit var binding: EpisodeDetailFragmentBinding
    private lateinit var viewModel: EpisodeViewModel
    private val dataEpisode: EpisodeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeDetailFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[EpisodeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataEpisode.dataEpisode.observe(viewLifecycleOwner){
            binding.tvNameEpisodeD.text = it.name
            binding.tvEpisode.text = it.episode
            binding.tvDataEpisode.text = it.air_date
        }
        binding.btnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        hideBottomNav()
    }
    private fun hideBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
    }
}