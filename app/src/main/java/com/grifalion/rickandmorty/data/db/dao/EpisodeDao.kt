package com.grifalion.rickandmorty.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.grifalion.rickandmorty.data.db.entity.episode.EpisodeDbModel
import com.grifalion.rickandmorty.data.db.entity.location.LocationDbModel

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(list: List<EpisodeDbModel>)

    @Query("SELECT * FROM item_episode WHERE (name LIKE  '%' || :searchName || '%' OR :searchName = '') " +
            "AND (episode LIKE :episode OR :episode = '') LIMIT :limit OFFSET :offset")
    suspend fun getAllEpisodesPage(offset: Int, limit: Int, searchName: String, episode: String): List<EpisodeDbModel>

    @Query("DELETE FROM item_episode")
    suspend fun deleteAllEpisodes()

    @Transaction
    suspend fun refreshEpisodes(list: List<EpisodeDbModel>){
        deleteAllEpisodes()
        insertEpisode(list)
    }
}