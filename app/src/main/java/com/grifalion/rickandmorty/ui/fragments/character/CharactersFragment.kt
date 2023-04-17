package com.grifalion.rickandmorty.ui.fragments.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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
import com.google.android.material.chip.Chip
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.ui.adapters.CharacterAdapter
import com.grifalion.rickandmorty.databinding.CharacterFragmentBinding
import com.grifalion.rickandmorty.data.network.models.Character
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharactersFragment: Fragment(), CharacterAdapter.Listener {
    private lateinit var binding: CharacterFragmentBinding
    private val adapter = CharacterAdapter(this)
    private val viewModels: MainViewModel by activityViewModels()
    private lateinit var viewModel: CharacterViewModel

    private var name = ""
    private var status = ""
    private var gender = ""
    private var species = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
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
                    viewModel.getCharactersTwo(name,status,gender,species)
                    viewModel.characterFlow.collectLatest(adapter::submitData)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                lifecycleScope.launch {
                    viewModel.getCharactersTwo(name,status,gender,species)
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
    }
    private fun initBottomFilter(){
        val dialogView: View = layoutInflater.inflate(R.layout.character_filter_fragment, null)
        val dialog = BottomSheetDialog(requireContext())
        val btnApply = dialogView.findViewById<Button>(R.id.btnApply)
        val chipAlive = dialogView.findViewById<Chip>(R.id.chip_alive)
        val chipDead = dialogView.findViewById<Chip>(R.id.chip_dead)
        val chipUnknown = dialogView.findViewById<Chip>(R.id.chip_unknown)
        val chipFemale = dialogView.findViewById<Chip>(R.id.chip_female)
        val chipMale = dialogView.findViewById<Chip>(R.id.chip_male)
        val chipGenderless = dialogView.findViewById<Chip>(R.id.chip_genderless)
        val chipUnknownGender = dialogView.findViewById<Chip>(R.id.chip_unknown_gender)
        val chipHuman = dialogView.findViewById<Chip>(R.id.chip_human)
        val chipAlien = dialogView.findViewById<Chip>(R.id.chip_alien)
        val chipHumanoid = dialogView.findViewById<Chip>(R.id.chip_humanoid)
        val chipRobot = dialogView.findViewById<Chip>(R.id.chip_robot)
        val chipUnknownSpecies = dialogView.findViewById<Chip>(R.id.chip_unknown_species)
        val chipPoopybutthole = dialogView.findViewById<Chip>(R.id.chip_poopybutthole)
        val chipMythological = dialogView.findViewById<Chip>(R.id.chip_mythological)
        val chipAnimal = dialogView.findViewById<Chip>(R.id.chip_animal)
        val chipCronenberg = dialogView.findViewById<Chip>(R.id.chip_cronenberg)
        val chipDisease = dialogView.findViewById<Chip>(R.id.chip_disease)
        val btnCloseDialog = dialogView.findViewById<ImageView>(R.id.btnCloseDialog)
        dialog.setContentView(dialogView)
        dialog.show()
        btnCloseDialog.setOnClickListener{ dialog.dismiss() }
        when(species){
            "Human" -> chipHuman.isChecked
            "Alien" -> chipAlien.isChecked
            "Humanoid" -> chipHumanoid.isChecked
            "Robot" -> chipRobot.isChecked
            "unknown" -> chipUnknownSpecies.isChecked
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
            if(chipUnknownSpecies.isChecked) species = "unknown"
            if(chipPoopybutthole.isChecked) species = "Poopybutthole"
            if(chipMythological.isChecked) species = "Mythological"
            if(chipAnimal.isChecked) species = "Animal"
            if(chipCronenberg.isChecked) species = "Cronenberg"
            if(chipDisease.isChecked) species = "Disease"

            if(chipAlive.isChecked || chipDead.isChecked || chipFemale.isChecked || chipGenderless.isChecked ||
                    chipMale.isChecked || chipFemale.isChecked || chipUnknown.isChecked || chipUnknownGender.isChecked
                || chipHuman.isChecked || chipAlien.isChecked || chipHumanoid.isChecked || chipRobot.isChecked ||
                    chipUnknownSpecies.isChecked || chipPoopybutthole.isChecked || chipMythological.isChecked ||
                    chipAnimal.isChecked || chipCronenberg.isChecked || chipDisease.isChecked){
                lifecycleScope.launch {
                    viewModel.getCharactersTwo(name,status,gender,species)
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
    private fun getListCharacters(){
        name = ""
        status = ""
        gender = ""
        species = ""
        lifecycleScope.launch {
            viewModel.getCharactersTwo(name,status,gender,species)
            viewModel.characterFlow.collectLatest(adapter::submitData)
        }
    }
}
