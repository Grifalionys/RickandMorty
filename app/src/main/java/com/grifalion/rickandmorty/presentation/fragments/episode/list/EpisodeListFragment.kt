package com.grifalion.rickandmorty.presentation.fragments.episode.list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.grifalion.rickandmorty.databinding.EpisodeFilterFragmentBinding
import com.grifalion.rickandmorty.databinding.EpisodeListFragmentBinding
import com.grifalion.rickandmorty.di.ViewModelFactory
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeListFragment: Fragment(), EpisodeListAdapter.ListenerEpisode {
    private lateinit var binding: EpisodeListFragmentBinding
    private lateinit var filterBinding: EpisodeFilterFragmentBinding
    private lateinit var viewModel: EpisodeListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (requireActivity().application as App).component
    }
    private lateinit var viewModelDetail: EpisodeDetailViewModel
    private val adapter = EpisodeListAdapter(this)
    private var name = EMPTY_STRING
    private var episode = EMPTY_STRING

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeListFragmentBinding.inflate(inflater)
        filterBinding = EpisodeFilterFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory)[EpisodeListViewModel::class.java]
        viewModelDetail = ViewModelProvider(requireActivity(),viewModelFactory)[EpisodeDetailViewModel::class.java]
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
        getListEpisodes()
        getNameSearchView()
        showBottomNav()
        swipeRefresh()
    }

    private fun showBottomNav(){
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun getNameSearchView(){
        binding.searchViewEp.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var name = query.toString()

                lifecycleScope.launchWhenStarted {
                    viewModel.getEpisodes(name,episode)
                    viewModel.episodeFlow.collectLatest(adapter::submitData)
                }
                    return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var name = newText.toString()
                lifecycleScope.launchWhenStarted {
                    viewModel.getEpisodes(name, episode)
                    viewModel.episodeFlow.collectLatest(adapter::submitData)
                }
                    return true
                }
        })
    }
    private fun swipeRefresh(){
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch{
                adapter.submitData(PagingData.empty())
                viewModel.episodeFlow.collectLatest(adapter::submitData)
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
        val seasonsArray = arrayOf("S01", "S02","S03", "S04", "S05")
        val episodeArray = arrayOf("E01", "E02", "E03","E04","E05","E06","E07","E08","E09","E10","E11")
        var selectSeason = EMPTY_STRING
        var selectEpisode = EMPTY_STRING
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

    override fun onClick(episode: EpisodeResult) {
        viewModelDetail.onClickItemEpisode(episode)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.containerFragment, EpisodeDetailFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun getListEpisodes() {
        name = EMPTY_STRING
        episode = EMPTY_STRING
        lifecycleScope.launchWhenStarted {
            viewModel.getEpisodes(name, episode)
            viewModel.episodeFlow.collectLatest(adapter::submitData)
        }
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