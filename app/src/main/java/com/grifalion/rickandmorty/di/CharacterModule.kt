package com.grifalion.rickandmorty.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.db.CharacterDatabase
import com.grifalion.rickandmorty.data.db.EpisodeDatabase
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.db.dao.EpisodeDao
import com.grifalion.rickandmorty.data.repository.CharacterRepositoryImpl
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import com.grifalion.rickandmorty.presentation.fragments.character.detail.CharacterDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharacterListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface CharacterModule {

    @Binds
    fun bindCharacterRepository(repository: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    fun bindCharacterViewModel(viewModel: CharacterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    fun bindCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel

    companion object{
        @Provides
        fun provideApiService(): CharacterApiService{
            return CharacterApiService.Companion.getInstance()
        }

        @Provides
        fun provideCharacterDao(application: Application): CharacterDao{
            return CharacterDatabase.getMainDatabase(application).getCharacterDao()
        }
    }
}