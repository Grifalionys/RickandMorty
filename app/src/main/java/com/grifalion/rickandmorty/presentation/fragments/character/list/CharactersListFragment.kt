package com.grifalion.rickandmorty.presentation.fragments.character.list

import android.content.Context
import android.net.ConnectivityManager
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
import androidx.paging.PagingData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.app.App
import com.grifalion.rickandmorty.data.mappers.CharacterMapper
import com.grifalion.rickandmorty.databinding.CharacterFilterFragmentBinding
import com.grifalion.rickandmorty.databinding.CharacterListFragmentBinding
import com.grifalion.rickandmorty.di.ViewModelFactory
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersListFragment: Fragment(), CharacterListAdapter.Listener {
    private lateinit var binding: CharacterListFragmentBinding
    private lateinit var filterBinding: CharacterFilterFragmentBinding
    private val adapter = CharacterListAdapter(this)
    private lateinit var viewModelList: CharacterListViewModel
    private lateinit var viewModelDetail: CharacterDetailViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var name = EMPTY_STRING
    private var status = EMPTY_STRING
    private var gender = EMPTY_STRING
    private var species = EMPTY_STRING

    override fun onAttach(context: Context) {
        (requireActivity().application as App).component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterListFragmentBinding.inflate(inflater)
        filterBinding = CharacterFilterFragmentBinding.inflate(layoutInflater)
        viewModelList = ViewModelProvider(requireActivity(),viewModelFactory)[CharacterListViewModel::class.java]
        viewModelDetail = ViewModelProvider(requireActivity(),viewModelFactory)[CharacterDetailViewModel::class.java]
        viewModelList.getCharacters(name,status,gender,species)
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
            swipeRefresh()
    }

     private fun getNameSearchView(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var name = query.toString()
                lifecycleScope.launch {
                    viewModelList.getCharacters(name,status,gender,species)
                    viewModelList.characterFlow.collectLatest(adapter::submitData)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                lifecycleScope.launch {
                    viewModelList.getCharacters(name,status,gender,species)
                    viewModelList.characterFlow.collectLatest(adapter::submitData)
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

    private fun swipeRefresh(){
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch{
                adapter.submitData(PagingData.empty())
                viewModelList.characterFlow.collectLatest(adapter::submitData)
            }
            binding.swipeRefresh.isRefreshing = false
        }
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

                    viewModelList.getCharacters(name,status,gender,species)
                    viewModelList.characterFlow.collectLatest(adapter::submitData)
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

    private fun showBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }
    private fun getListCharacters(){
        lifecycleScope.launch {
            name = EMPTY_STRING
            status = EMPTY_STRING
            gender = EMPTY_STRING
            species = EMPTY_STRING
            viewModelList.getCharacters(name,status,gender,species)
            viewModelList.characterFlow.collectLatest(adapter::submitData)
        }
    }

    override fun onClick(character: CharacterResult) {
        viewModelDetail.onClickItemCharacter(character)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, CharacterDetailFragment())
            .addToBackStack("characters")
            .commit()

    }

    companion object{
        private const val EMPTY_STRING = ""
    }
}



