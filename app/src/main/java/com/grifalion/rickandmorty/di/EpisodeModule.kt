package com.grifalion.rickandmorty.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.grifalion.rickandmorty.data.api.EpisodeApiService
import com.grifalion.rickandmorty.data.db.EpisodeDatabase
import com.grifalion.rickandmorty.data.db.LocationDatabase
import com.grifalion.rickandmorty.data.db.dao.EpisodeDao
import com.grifalion.rickandmorty.data.db.dao.LocationDao
import com.grifalion.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.grifalion.rickandmorty.domain.repository.EpisodeRepository
import com.grifalion.rickandmorty.presentation.fragments.episode.detail.EpisodeDetailViewModel
import com.grifalion.rickandmorty.presentation.fragments.episode.list.EpisodeListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface EpisodeModule {

    @Binds
    fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeListViewModel::class)
    fun bindEpisodeViewModel(viewModel: EpisodeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailViewModel::class)
    fun bindEpisodeDetailViewModel(viewModel: EpisodeDetailViewModel): ViewModel


    companion object {
        @Provides
        fun provideApiService(): EpisodeApiService {
            return EpisodeApiService.Companion.getInstance()
        }

        @Provides
        fun provideEpisodeDao(application: Application): EpisodeDao {
            return EpisodeDatabase.getMainDatabase(application).getEpisodeDao()
        }
    }
}