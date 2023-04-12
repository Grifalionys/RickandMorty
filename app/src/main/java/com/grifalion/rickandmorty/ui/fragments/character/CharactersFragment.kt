package com.grifalion.rickandmorty.ui.fragments.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.ui.adapters.CharacterAdapter
import com.grifalion.rickandmorty.databinding.CharacterFragmentBinding
import com.grifalion.rickandmorty.data.models.Character
import com.grifalion.rickandmorty.data.network.CharacterApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment: Fragment(), CharacterAdapter.Listener {
    private lateinit var binding: CharacterFragmentBinding
    private val adapter = CharacterAdapter(this)
    private val viewModels: MainViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterFragmentBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNav()
        binding.rvCharacters.adapter = adapter
        val vm = ViewModelProvider(this,CharactersViewModelFactory(CharacterApiService.api))[CharactersViewModel::class.java]
        with(vm){
            lifecycleScope.launch(Dispatchers.IO){
                vm.characters.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

    }

    override fun onClick(character: Character) {
        viewModels.dataCharacter.value = character
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment,DetailCharacterFragment::class.java.newInstance())
            .addToBackStack("characters")
            .commit()
    }
    private fun showBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }
}
