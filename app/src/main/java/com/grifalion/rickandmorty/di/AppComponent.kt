package com.example.astonproject.app.di

import android.app.Application
import com.grifalion.rickandmorty.app.App
import com.grifalion.rickandmorty.di.CharacterModule
import com.grifalion.rickandmorty.di.EpisodeModule
import com.grifalion.rickandmorty.di.LocationModule
import com.grifalion.rickandmorty.presentation.MainActivity
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharactersListFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.list.EpisodeListFragment
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailFragment
import com.grifalion.rickandmorty.presentation.fragments.location.list.LocationsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CharacterModule::class, EpisodeModule::class, LocationModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersListFragment)
    fun inject(fragment: CharacterDetailFragment)
    fun inject(fragment: EpisodeListFragment)
    fun inject(fragment: EpisodeDetailFragment)
    fun inject(fragment: LocationsListFragment)
    fun inject(fragment: LocationDetailFragment)

    fun inject(application: App)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

}
