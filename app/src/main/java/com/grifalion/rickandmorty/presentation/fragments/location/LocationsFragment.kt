package com.grifalion.rickandmorty.presentation.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.LocationFragmentBinding
import com.grifalion.rickandmorty.presentation.adapters.LocationAdapter
import com.grifalion.rickandmorty.presentation.fragments.character.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationsFragment: Fragment() {
    private lateinit var binding: LocationFragmentBinding
    private val adapter = LocationAdapter()
    private val viewModels: MainViewModel by activityViewModels()
    private lateinit var viewModel: LocationViewModel
    private var name = ""
    private var type = ""
    private var dimension = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomFilter()
        binding.rvLocation.adapter = adapter
        adapter.addLoadStateListener { loadState->
            if(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading){
                binding.progressBarLocation.visibility = View.VISIBLE
            } else {
                binding.progressBarLocation.visibility = View.GONE
            }
        }
        getListLocations()
        getNameSearchView()
    }



    private fun getNameSearchView(){
        binding.searchViewLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var name = query.toString()
                lifecycleScope.launch {
                    viewModel.getLocations(name,type,dimension)
                    viewModel.locationFlow.collectLatest(adapter::submitData)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                lifecycleScope.launch {
                    viewModel.getLocations(name,type,dimension)
                    viewModel.locationFlow.collectLatest(adapter::submitData)
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
    private fun initBottomFilter() {
        val dialogView: View = layoutInflater.inflate(R.layout.location_filter_fragment, null)
        val dialog = BottomSheetDialog(requireContext())
        val btnApply = dialogView.findViewById<Button>(R.id.btnApply)

        val chipPlanet = dialogView.findViewById<Chip>(R.id.chip_planet)
        val chipCluster = dialogView.findViewById<Chip>(R.id.chip_cluster)
        val chipSpaceStation = dialogView.findViewById<Chip>(R.id.chip_space_station)
        val chipTv = dialogView.findViewById<Chip>(R.id.chip_tv)
        val chipUnknownType = dialogView.findViewById<Chip>(R.id.chip_unknown_type)
        val chipMicroverse = dialogView.findViewById<Chip>(R.id.chip_microverse)
        val chipResort = dialogView.findViewById<Chip>(R.id.chip_resort)
        val chipFantasyTown = dialogView.findViewById<Chip>(R.id.chip_fantasy_town)
        val chipDream = dialogView.findViewById<Chip>(R.id.chip_dream)
        val chipMenagerie = dialogView.findViewById<Chip>(R.id.chip_menagerie)
        val chipGame = dialogView.findViewById<Chip>(R.id.chip_game)
        val chipCustoms = dialogView.findViewById<Chip>(R.id.chip_customs)
        val chipDaycare = dialogView.findViewById<Chip>(R.id.chip_daycare)
        val chip137 = dialogView.findViewById<Chip>(R.id.chip_137)
        val chipApocaliptic = dialogView.findViewById<Chip>(R.id.chip_apocaliptic)
        val chipReplacement = dialogView.findViewById<Chip>(R.id.chip_replacement)
        val chipFantasy = dialogView.findViewById<Chip>(R.id.chip_fantasy)
        val chip5 = dialogView.findViewById<Chip>(R.id.chip_5)
        val chipUnknownDimension = dialogView.findViewById<Chip>(R.id.chip_unknown_dimension)

        val btnCloseDialog = dialogView.findViewById<ImageView>(R.id.btnCloseDialog)
        dialog.setContentView(dialogView)
        dialog.show()
        btnCloseDialog.setOnClickListener { dialog.dismiss() }

        when(type){
            "Planet" -> chipPlanet.isChecked
            "Cluster" -> chipCluster.isChecked
            "Space station" -> chipSpaceStation.isChecked
            "TV" -> chipTv.isChecked
            "unknown" -> chipUnknownType.isChecked
            "Microverse" -> chipMicroverse.isChecked
            "Resort" -> chipResort.isChecked
            "Fantasy town" -> chipFantasyTown.isChecked
            "Dream" -> chipDream.isChecked
            "Menagerie" -> chipMenagerie.isChecked
            "Game" -> chipGame.isChecked
            "Customs" -> chipCustoms.isChecked
            "Daycare" -> chipDaycare.isChecked
        }

        when(dimension){
            "Dimension C-137" -> chip137.isChecked
            "Post-Apocalyptic Dimension" -> chipApocaliptic.isChecked
            "Replacement Dimension" -> chipReplacement.isChecked
            "Fantasy Dimension" -> chipFantasy.isChecked
            "Dimension 5-126" -> chip5.isChecked
            "unknown" -> chipUnknownDimension.isChecked


        }
        btnApply.setOnClickListener {
            if(chipPlanet.isChecked) type = "Planet"
            if(chipCluster.isChecked) type = "Cluster"
            if(chipSpaceStation.isChecked) type = "Space station"
            if(chipTv.isChecked) type = "TV"
            if(chipUnknownType.isChecked) type = "unknown"
            if(chipMicroverse.isChecked) type = "Microverse"
            if(chipResort.isChecked) type = "Resort"
            if(chipFantasyTown.isChecked) type = "Fantasy town"
            if(chipDream.isChecked) type = "Dream"
            if(chipMenagerie.isChecked) type = "Menagerie"
            if(chipGame.isChecked) type = "Game"
            if(chipCustoms.isChecked) type = "Customs"
            if(chipDaycare.isChecked) type = "Daycare"
            if(chip137.isChecked) dimension = "Dimension C-137"
            if(chipApocaliptic.isChecked) dimension = "Post-Apocalyptic Dimension"
            if(chipReplacement.isChecked) dimension = "Replacement Dimension"
            if(chipFantasy.isChecked) dimension = "Fantasy Dimension"
            if(chip5.isChecked) dimension = "Dimension 5-126"
            if(chipUnknownDimension.isChecked) dimension = "unknown"

            if(chipPlanet.isChecked || chipCluster.isChecked || chipSpaceStation.isChecked || chipTv.isChecked || chipUnknownType.isChecked ||
                chipMicroverse.isChecked || chipResort.isChecked || chipFantasyTown.isChecked || chipDream.isChecked || chipMenagerie.isChecked ||
                chipGame.isChecked || chipCustoms.isChecked || chipDaycare.isChecked || chip137.isChecked || chipApocaliptic.isChecked ||
                    chipReplacement.isChecked || chipFantasy.isChecked || chip5.isChecked || chipUnknownDimension.isChecked){
                lifecycleScope.launch {
                    viewModel.getLocations(name, type, dimension)
                    viewModel.locationFlow.collectLatest(adapter::submitData)
                }
                dialog.dismiss()
                binding.btnFilter.visibility = View.GONE
                binding.btnCloseFilter.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(),getString(R.string.error_parameters), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCloseFilter.setOnClickListener {
            binding.btnCloseFilter.visibility = View.GONE
            binding.btnFilter.visibility = View.VISIBLE
            getListLocations()
        }
    }

    private fun getListLocations(){
        name = ""
        type = ""
        dimension = ""
        lifecycleScope.launch {
            viewModel.getLocations(name,type,dimension)
            viewModel.locationFlow.collectLatest(adapter::submitData)
        }
    }
}