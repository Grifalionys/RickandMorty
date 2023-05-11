package com.grifalion.rickandmorty.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.api.LocationApiService
import com.grifalion.rickandmorty.data.db.LocationDatabase
import com.grifalion.rickandmorty.data.db.dao.LocationDao
import com.grifalion.rickandmorty.data.repository.LocationRepositoryImpl
import com.grifalion.rickandmorty.domain.repository.LocationRepository
import com.grifalion.rickandmorty.presentation.fragments.location.detail.LocationDetailViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailViewModel::class)
    fun bindLocationDetailViewModel(viewModel: LocationDetailViewModel): ViewModel

    companion object {
        @Provides
        fun provideApiService(): LocationApiService {
            return LocationApiService.getInstance()
        }

        @Provides
        fun provideLocationDao(application: Application): LocationDao {
            return LocationDatabase.getMainDatabase(application).getLocationDao()
        }
    }
}