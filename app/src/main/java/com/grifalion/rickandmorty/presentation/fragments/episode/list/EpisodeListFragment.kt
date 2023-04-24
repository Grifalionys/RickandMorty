package com.grifalion.rickandmorty.presentation.fragments.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.EpisodeListFragmentBinding
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.DetailEpisodeFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment: Fragment(), EpisodeListAdapter.ListenerEpisode {
    private lateinit var binding: EpisodeListFragmentBinding
    private val adapter = EpisodeListAdapter(this)
    private lateinit var viewModel: EpisodeListViewModel
    private val dataEpisode: EpisodeListViewModel by activityViewModels()
    private var name = ""
    private var episode = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeListFragmentBinding.inflate(inflater)
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
    private fun initBottomFilter() {
        val dialogView: View = layoutInflater.inflate(R.layout.character_filter_fragment, null)
        val dialog = BottomSheetDialog(requireContext())
        val btnApply = dialogView.findViewById<Button>(R.id.btnApply)
        val btnCloseDialog = dialogView.findViewById<ImageView>(R.id.btnCloseDialog)
        dialog.setContentView(dialogView)
        dialog.show()
        btnCloseDialog.setOnClickListener {
            dialog.dismiss()


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
        dataEpisode.dataEpisode.postValue(episode)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, DetailEpisodeFragment::class.java.newInstance())
            .addToBackStack("episodes")
            .commit()
    }
}