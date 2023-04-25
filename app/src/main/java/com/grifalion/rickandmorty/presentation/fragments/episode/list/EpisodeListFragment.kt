package com.grifalion.rickandmorty.presentation.fragments.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.EpisodeFilterFragmentBinding
import com.grifalion.rickandmorty.databinding.EpisodeListFragmentBinding
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment: Fragment(), EpisodeListAdapter.ListenerEpisode {
    private lateinit var binding: EpisodeListFragmentBinding
    private lateinit var filterBinding: EpisodeFilterFragmentBinding
    private lateinit var viewModel: EpisodeListViewModel
    private val viewModelDetail: CharacterDetailViewModel by activityViewModels()
    private val adapter = EpisodeListAdapter(this)
    private var name = ""
    private var episode = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeListFragmentBinding.inflate(inflater)
        filterBinding = EpisodeFilterFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[EpisodeListViewModel::class.java]
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomFilter()
        binding.rvEpisode.adapter = adapter
        adapter.addLoadStateListener { loadState->
            if(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading){
                binding.progressBarEpisode.visibility = View.VISIBLE
            } else {
                binding.progressBarEpisode.visibility = View.GONE
            }
        }
        getListLocations()
        getNameSearchView()
        showBottomNav()
    }

    private fun showBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun getNameSearchView(){
        binding.searchViewEp.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var name = query.toString()
                lifecycleScope.launch {
                    viewModel.getEpisodes(name,episode)
                    viewModel.episodeFlow.collectLatest(adapter::submitData)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                lifecycleScope.launch {
                    viewModel.getEpisodes(name,episode)
                    viewModel.episodeFlow.collectLatest(adapter::submitData)
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
    private fun initBottomFilter() = with(filterBinding) {
        val dialog = BottomSheetDialog(requireContext())
        val seasonsArray = arrayOf("S01", "S02","S03", "S04", "S05")
        val episodeArray = arrayOf("E01", "E02", "E03","E04","E05","E06","E07","E08","E09","E10","E11")
        var selectSeason = ""
        var selectEpisode = ""
        val seasonsAdapter = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_dropdown_item,seasonsArray)
        val episodeAdapter = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_dropdown_item,episodeArray)
        spinnerSeason.adapter = seasonsAdapter

        spinnerSeason.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectSeason = seasonsArray[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        spinnerEpisode.adapter = episodeAdapter
        spinnerEpisode.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectEpisode = episodeArray[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        if(filterBinding.root.parent != null){
            (filterBinding.root.parent as ViewGroup).removeView(filterBinding.root)
        }
        dialog.setContentView(filterBinding.root)
        dialog.show()
        btnCloseEpisodeDialog.setOnClickListener {
            dialog.dismiss()
        }
        btnApplyFilterEp.setOnClickListener {
            episode = selectSeason + selectEpisode
            lifecycleScope.launch {
                viewModel.getEpisodes(name,episode)
                viewModel.episodeFlow.collectLatest(adapter::submitData)
            }
            binding.btnFilter.visibility = View.GONE
            binding.btnCloseFilter.visibility = View.VISIBLE
            dialog.dismiss()
        }
        binding.btnCloseFilter.setOnClickListener {
            binding.btnCloseFilter.visibility = View.GONE
            binding.btnFilter.visibility = View.VISIBLE
            getListEpisodes()

        }
    }

    private fun getListLocations(){
        name = ""
        episode = ""
        lifecycleScope.launch {
            viewModel.getEpisodes(name,episode)
            viewModel.episodeFlow.collectLatest(adapter::submitData)
        }
    }

    override fun onClick(episode: Episode) {
        viewModelDetail.onClickItemEpisode(episode);
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, EpisodeDetailFragment(viewModelDetail))
            .addToBackStack("episodes")
            .commit()
    }

    private fun getListEpisodes() {
        name = ""
        episode = ""
        lifecycleScope.launch {
            viewModel.getEpisodes(name, episode)
            viewModel.episodeFlow.collectLatest(adapter::submitData)
        }
    }
}