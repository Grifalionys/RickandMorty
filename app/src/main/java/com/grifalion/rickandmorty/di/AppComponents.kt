package com.grifalion.rickandmorty.di

import androidx.room.Database
import com.grifalion.rickandmorty.presentation.MainActivity
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharacterListViewModel
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharactersListFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.episode.list.EpisodeListFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.list.EpisodeListViewModel
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.location.list.LocationListViewModel
import com.grifalion.rickandmorty.presentation.fragments.location.list.LocationsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponents {

    fun inject(mainActivity: MainActivity)

    fun inject(charactersListFragment: CharactersListFragment)
    fun inject(characterListViewModel: CharacterListViewModel)
    fun inject(characterDetailFragment: CharacterDetailFragment)
    fun inject(characterDetailViewModel: CharacterDetailViewModel)

    fun inject(locationListFragment: LocationsListFragment)
    fun inject(locationListViewModel: LocationListViewModel)
    fun inject(locationDetailViewModel: LocationDetailViewModel)
    fun inject(locationDetailFragment: LocationDetailFragment)

    fun inject(episodeListFragment: EpisodeListFragment)
    fun inject(episodeListViewModel: EpisodeListViewModel)
    fun inject(episodeDetailFragment: EpisodeDetailFragment)
    fun inject(episodeDetailViewModel: EpisodeDetailViewModel)

}