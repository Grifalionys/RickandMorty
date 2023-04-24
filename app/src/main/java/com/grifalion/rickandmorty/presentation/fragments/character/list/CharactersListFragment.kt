package com.grifalion.rickandmorty.presentation.fragments.character.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.CharacterFilterFragmentBinding
import com.grifalion.rickandmorty.databinding.CharacterListFragmentBinding
import com.grifalion.rickandmorty.domain.models.character.Character
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharactersListFragment: Fragment(), CharacterListAdapter.Listener {
    private lateinit var binding: CharacterListFragmentBinding
    private lateinit var filterBinding: CharacterFilterFragmentBinding
    private val adapter = CharacterListAdapter(this)
    private val detailVM: CharacterDetailViewModel by activityViewModels()
    private lateinit var viewModel: CharacterListViewModel

    private var name = ""
    private var status = ""
    private var gender = ""
    private var species = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterListFragmentBinding.inflate(inflater)
        filterBinding = CharacterFilterFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNav()
        binding.rvCharacters.adapter = adapter
        adapter.addLoadStateListener { loadState->
            if(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        getListCharacters()
        getNameSearchView()
        showBottomFilter()
    }



     private fun getNameSearchView(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var name = query.toString()
                lifecycleScope.launch {
                    viewModel.getCharacters(id,name,status,gender,species)
                    viewModel.characterFlow.collectLatest(adapter::submitData)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                lifecycleScope.launch {
                    viewModel.getCharacters(id,name,status,gender,species)
                    viewModel.characterFlow.collectLatest(adapter::submitData)
                }
                return true
            }

        })

    }
    private fun showBottomFilter(){
        binding.btnFilter.setOnClickListener{
          initBottomFilter()
        }
        BottomSheetDialog(requireContext())
    }

    private fun initBottomFilter() = with((filterBinding)){
        val dialog = BottomSheetDialog(requireContext())
        if(filterBinding.root.parent != null){
            (filterBinding.root.parent as ViewGroup).removeView(filterBinding.root)
        }
        dialog.setContentView(filterBinding.root)
        dialog.show()
        btnCloseDialog.setOnClickListener{ dialog.dismiss() }
        btnClearText.setOnClickListener { edSearchHero.text.clear() }

        when(species){
            "Human" -> chipHuman.isChecked
            "Alien" -> chipAlien.isChecked
            "Humanoid" -> chipHumanoid.isChecked
            "Robot" -> chipRobot.isChecked
            "unknown" -> chipUnknownHero.isChecked
            "Poopybutthole" -> chipPoopybutthole.isChecked
            "Mythological" -> chipMythological.isChecked
            "Animal" -> chipAnimal.isChecked
            "Cronenberg" -> chipCronenberg.isChecked
            "Disease" -> chipDisease.isChecked
        }
        when(status){
            "Alive" -> chipAlive.isChecked
            "Dead" -> chipDead.isChecked
            "unknown" -> chipUnknown.isChecked
        }
        when(gender){
            "Female" -> chipFemale.isChecked
            "Male" -> chipMale.isChecked
            "Genderless" -> chipGenderless.isChecked
            "unknown" -> chipUnknownGender.isChecked
        }
        btnApply.setOnClickListener {
            if(edSearchHero.text.isNotEmpty()) name = edSearchHero.text.toString()
            if(chipAlive.isChecked) status = "Alive"
            if(chipDead.isChecked) status = "Dead"
            if(chipUnknown.isChecked) status = "unknown"
            if(chipFemale.isChecked) gender = "Female"
            if(chipMale.isChecked) gender = "Male"
            if(chipGenderless.isChecked) gender = "Genderless"
            if(chipUnknownGender.isChecked) gender = "unknown"
            if(chipHuman.isChecked) species = "Human"
            if(chipAlien.isChecked) species = "Alien"
            if(chipHumanoid.isChecked) species = "Humanoid"
            if(chipRobot.isChecked) species = "Robot"
            if(chipUnknownHero.isChecked) species = "unknown"
            if(chipPoopybutthole.isChecked) species = "Poopybutthole"
            if(chipMythological.isChecked) species = "Mythological"
            if(chipAnimal.isChecked) species = "Animal"
            if(chipCronenberg.isChecked) species = "Cronenberg"
            if(chipDisease.isChecked) species = "Disease"

            if(chipAlive.isChecked || chipDead.isChecked || chipFemale.isChecked || chipGenderless.isChecked ||
                    chipMale.isChecked || chipFemale.isChecked || chipUnknown.isChecked || chipUnknownGender.isChecked
                || chipHuman.isChecked || chipAlien.isChecked || chipHumanoid.isChecked || chipRobot.isChecked ||
                    chipUnknownHero.isChecked || chipPoopybutthole.isChecked || chipMythological.isChecked ||
                    chipAnimal.isChecked || chipCronenberg.isChecked || chipDisease.isChecked || edSearchHero.text.isNotEmpty()){
                lifecycleScope.launch {
                    viewModel.getCharacters(id,name,status,gender,species)
                    viewModel.characterFlow.collectLatest(adapter::submitData)

                }
                dialog.dismiss()
                binding.btnFilter.visibility = View.GONE
                binding.btnCloseFilter.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(),getString(R.string.error_parameters),Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCloseFilter.setOnClickListener {
            binding.btnCloseFilter.visibility = View.GONE
            binding.btnFilter.visibility = View.VISIBLE
            getListCharacters()

        }
    }


    override fun onClick(character: Character) {
        detailVM.onClickItemCharacter(character)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, CharacterDetailFragment(detailVM))
            .addToBackStack("characters")
            .commit()
    }
    private fun showBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }
    private fun getListCharacters(){
        name = ""
        status = ""
        gender = ""
        species = ""
        lifecycleScope.launch {
            viewModel.getCharacters(id,name,status,gender,species)
            viewModel.characterFlow.collectLatest(adapter::submitData)
        }
    }



}


