package com.grifalion.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.api.LocationApiService
import com.grifalion.rickandmorty.data.repository.LocationRepositoryImpl
import com.grifalion.rickandmorty.domain.repository.LocationRepository
import com.grifalion.rickandmorty.presentation.fragments.location.list.LocationListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface LocationModule {

    @Binds
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel::class)
    fun bindLocationViewModel(viewModel: LocationListViewModel): ViewModel



    companion object {
        @Provides
        fun provideApiService(): LocationApiService {
            return LocationApiService.Companion.getInstance()
        }
    }
}