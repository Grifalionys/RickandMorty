package com.grifalion.rickandmorty.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grifalion.rickandmorty.data.db.dao.CharacterDao


abstract class MainDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    companion object{
        private var database: MainDatabase? = null

        fun getMainDatabase(context: Context): MainDatabase{
            return if(database==null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "maindb"
                ).build()
                database as MainDatabase
            } else{
                database as MainDatabase
            }
        }
    }
}