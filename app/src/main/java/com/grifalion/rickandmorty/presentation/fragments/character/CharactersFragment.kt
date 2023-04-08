package com.grifalion.rickandmorty.presentation.fragments.character

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.grifalion.rickandmorty.adapters.CharacterAdapter
import com.grifalion.rickandmorty.databinding.CharacterFragmentBinding
import com.grifalion.rickandmorty.repository.Repository

class CharactersFragment: Fragment() {
    private lateinit var binding: CharacterFragmentBinding
    private val adapter = CharacterAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.adapter = adapter
        val viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]
        viewModel.getCharacters(1)
        viewModel.charactersList.observe(viewLifecycleOwner) { response ->
            if(response.isSuccessful){
                adapter.addCharacter(response.body()!!.results)
            }

        }
    }
}
