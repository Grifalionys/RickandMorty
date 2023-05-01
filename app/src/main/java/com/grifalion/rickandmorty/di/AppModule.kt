package com.grifalion.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.repository.CharacterRepositoryImpl
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharacterListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface AppModule {

    @Binds
    fun bindCharacterRepository(repository: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    fun bindCharacterViewModel(viewModel: CharacterListViewModel): ViewModel

    companion object{
        @Provides
        fun provideApiService(): CharacterApiService{
            return CharacterApiService.Companion.CharacterRetrofit.characterApiService
        }
    }
}