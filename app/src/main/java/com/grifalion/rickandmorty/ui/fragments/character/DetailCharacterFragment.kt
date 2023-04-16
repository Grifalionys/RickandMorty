package com.grifalion.rickandmorty.ui.fragments.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.data.network.CharacterApiService
import com.grifalion.rickandmorty.databinding.CharacterDetailFragmentBinding

class DetailCharacterFragment: Fragment() {
    private lateinit var binding: CharacterDetailFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()
        viewModel.dataCharacter.observe(viewLifecycleOwner){
            Glide.with(binding.imIconDetail)
                .load(it.image)
                .into(binding.imIconDetail)
            binding.tvStatusDeatail.text = it.status
            binding.tvNameDetail.text = it.name
            binding.tvGenderDetail.text = it.gender
            binding.tvSpeciesDetail.text = it.species
            binding.tvLocationDetail.text = it.origin.name
            binding.tvTest.text = it.location.name
            binding.btnBack.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            lifeOrDeath()
        }
    }

    private fun lifeOrDeath(){
        if(binding.tvStatusDeatail.text.equals(getString(R.string.alive_ex))){
            binding.tvStatusDeatail.setBackgroundResource(R.drawable.view_alive)
        } else if(binding.tvStatusDeatail.text.equals(getString(R.string.dead_ex))){
            binding.tvStatusDeatail.setBackgroundResource(R.drawable.view_dead)
        } else if(binding.tvStatusDeatail.text.equals("unknown")){
            binding.tvStatusDeatail.setBackgroundResource(R.drawable.view_unknown)
        }
    }

    private fun hideBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
    }
}