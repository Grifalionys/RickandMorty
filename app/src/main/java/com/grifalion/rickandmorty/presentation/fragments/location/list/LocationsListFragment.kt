package com.grifalion.rickandmorty.presentation.fragments.location.list

import android.content.Context
import android.net.ConnectivityManager
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
import androidx.paging.PagingData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.app.App
import com.grifalion.rickandmorty.databinding.CharacterFilterFragmentBinding
import com.grifalion.rickandmorty.databinding.LocationFilterFragmentBinding
import com.grifalion.rickandmorty.databinding.LocationListFragmentBinding
import com.grifalion.rickandmorty.di.ViewModelFactory
import com.grifalion.rickandmorty.domain.models.location.Location
import com.grifalion.rickandmorty.domain.models.location.LocationResult
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsListFragment: Fragment(), LocationListAdapter.Listener {
    private lateinit var binding: LocationListFragmentBinding
    private lateinit var filterBinding: LocationFilterFragmentBinding
    private val adapter = LocationListAdapter(this)
    private lateinit var viewModel: LocationListViewModel
    private lateinit var locationDetailViewModel: LocationDetailViewModel
    private var name = EMPTY_STRING
    private var type = EMPTY_STRING
    private var dimension = EMPTY_STRING

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
        binding = LocationListFragmentBinding.inflate(inflater)
        filterBinding = LocationFilterFragmentBinding.inflate(layoutInflater)
        locationDetailViewModel = ViewModelProvider(requireActivity(),viewModelFactory)[LocationDetailViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory)[LocationListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNav()
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
        swipeRefresh()
    }

    private fun getNameSearchView(){
        binding.searchViewLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var name = query.toString()
                if (hasConnected(requireContext())) {
                lifecycleScope.launch {
                    viewModel.getLocations(name,type,dimension)
                    viewModel.locationFlow.collectLatest(adapter::submitData)
                }
                return true
                } else {
                    Toast.makeText(requireContext(),getString(R.string.error_network), Toast.LENGTH_SHORT).show()
                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                if (hasConnected(requireContext())) {
                lifecycleScope.launch {
                    viewModel.getLocations(name,type,dimension)
                    viewModel.locationFlow.collectLatest(adapter::submitData)
                }
                return true
                } else {
                    Toast.makeText(requireContext(),getString(R.string.error_network), Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        })
    }

    private fun swipeRefresh(){
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch{
                adapter.submitData(PagingData.empty())
                viewModel.locationFlow.collectLatest(adapter::submitData)
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showBottomFilter(){
        binding.btnFilter.setOnClickListener{
            initBottomFilter()
        }
    }

    private fun initBottomFilter() = with(filterBinding) {
        val dialog = BottomSheetDialog(requireContext())
        if(filterBinding.root.parent != null){
            (filterBinding.root.parent as ViewGroup).removeView(filterBinding.root)
        }
        dialog.setContentView(filterBinding.root)
        dialog.show()
        filterBinding.btnCloseDialog.setOnClickListener { dialog.dismiss() }
        when(type){
            "Planet" -> chipPlanet.isChecked
            "Cluster" -> chipCluster.isChecked
            "Space station" -> chipSpace.isChecked
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
            if(chipSpace.isChecked) type = "Space station"
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
            if(chipPlanet.isChecked || chipCluster.isChecked || chipSpace.isChecked || chipTv.isChecked || chipUnknownType.isChecked ||
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

    override fun onClick(location: LocationResult) {
        locationDetailViewModel.onClickItemCharacter(location)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, LocationDetailFragment())
            .addToBackStack("locations")
            .commit()
    }
    private fun showBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hasConnected(context: Context): Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = manager.activeNetworkInfo
        return network != null && network.isConnected
    }

    companion object{
        private const val EMPTY_STRING = ""
    }
}