package com.grifalion.rickandmorty.data.db

import androidx.room.RoomDatabase
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.db.dao.EpisodeDao
import com.grifalion.rickandmorty.data.db.dao.LocationDao

abstract class MainDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getLocationDao(): LocationDao

}