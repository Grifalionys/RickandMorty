package com.grifalion.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.grifalion.rickandmorty.data.db.MainDatabase
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.db.dao.EpisodeDao
import com.grifalion.rickandmorty.data.db.dao.LocationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MainDatabase{
        return Room.databaseBuilder(context,MainDatabase::class.java,"database")
            .build()
    }

    @Provides
    @Singleton
    fun providedCharacterDao(database: MainDatabase): CharacterDao{
        return database.getCharacterDao()
    }

    @Provides
    @Singleton
    fun providedLocationDao(database: MainDatabase): LocationDao{
        return database.getLocationDao()
    }

    @Provides
    @Singleton
    fun providedEpisodeDao(database: MainDatabase): EpisodeDao{
        return database.getEpisodeDao()
    }
}