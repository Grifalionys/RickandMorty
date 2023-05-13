package com.grifalion.rickandmorty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grifalion.rickandmorty.data.db.dao.EpisodeDao
import com.grifalion.rickandmorty.data.db.entity.episode.EpisodeDbModel

@Database(entities = [EpisodeDbModel::class], version = 2)
abstract class EpisodeDatabase: RoomDatabase() {
    abstract fun getEpisodeDao(): EpisodeDao

    companion object{
        private var database: EpisodeDatabase? = null
        private val ANY = Any()

        fun getMainDatabase(context: Context): EpisodeDatabase{
            synchronized(ANY){
                database?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EpisodeDatabase::class.java,
                    "episodeDb"
                ).build()
                database = instance
                return instance
            }
        }
    }

}