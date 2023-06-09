package com.grifalion.rickandmorty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.db.entity.character.CharacterDbModel


@Database(entities = [CharacterDbModel::class], version = 7)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    companion object{
        private var database: CharacterDatabase? = null
        private val ANY = Any()

        fun getMainDatabase(context: Context): CharacterDatabase{
        synchronized(ANY){
            database?.let {
                return it
            }
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CharacterDatabase::class.java,
                "characterDb"
            ).build()
                database = instance
                return instance
            }
        }
    }
}