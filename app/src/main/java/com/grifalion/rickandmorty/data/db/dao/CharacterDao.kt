package com.grifalion.rickandmorty.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.grifalion.rickandmorty.data.db.entity.character.CharacterDbModel

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(list: List<CharacterDbModel>)

    @Query("SELECT * FROM item_character")
    fun getAllCharacters(): List<CharacterDbModel>

    @Query("DELETE FROM item_character")
    suspend fun deleteAllCharacters()

    @Transaction
    suspend fun refreshCharacters(list: List<CharacterDbModel>){
        deleteAllCharacters()
        insertCharacter(list)
    }
    @Query("SELECT * FROM item_character WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
            "AND (:status IS NULL OR status LIKE :status)" +
            "AND (:species IS NULL OR species LIKE '%' || :species || '%')" +
            "AND (:type IS NULL OR type LIKE '%' || :type || '%')" +
            "AND (:gender IS NULL OR gender LIKE :gender)")
    fun getFilteredCharacters(name: String?, status: String?, species: String?, type: String?, gender: String?): List<CharacterDbModel>
}