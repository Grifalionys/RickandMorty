package com.grifalion.rickandmorty.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grifalion.rickandmorty.data.db.entity.EpisodeEntity

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEpisodes(episodes: EpisodeEntity)

    @Query("SELECT * FROM episodes_table WHERE id = :id")
    fun getEpisodeById(id: Int): EpisodeEntity

    @Query("SELECT * FROM episodes_table WHERE (:name IS NULL OR name LIKE '%' || :name || '%') " +
            "AND (:episode IS NULL OR episode LIKE '%' || :episode || '%')")
    fun getFilteredEpisodes(name: String?, episode: String?): List<EpisodeEntity>
}