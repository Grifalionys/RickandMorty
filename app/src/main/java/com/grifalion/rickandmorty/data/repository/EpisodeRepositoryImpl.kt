package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.api.EpisodeApiService
import com.grifalion.rickandmorty.data.db.dao.EpisodeDao
import com.grifalion.rickandmorty.data.mappers.EpisodeMapper
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.episode.Episode
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.repository.EpisodeRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val apiService: EpisodeApiService,
    private val episodeDao: EpisodeDao,
    private val mapper: EpisodeMapper
): EpisodeRepository {

    override suspend fun getEpisode(page: Int, name: String, episode: String): Episode {
        val episodeApi = apiService.getEpisode(page,name,episode)
        val listEpisodes = mapper.mapEpisodeResponseForEpisode(episodeApi)
        episodeDao.insertEpisode(mapper.mapListResultResponseForListDb(listEpisodes.results))
        return listEpisodes
    }

    override suspend fun insertEpisode(list: List<EpisodeResult>) {
        episodeDao.insertEpisode(mapper.mapListResultResponseForListDb(list))
    }

    override fun getListEpisodesDb(): List<EpisodeResult> {
        var listEpisodes = emptyList<EpisodeResult>()
        CoroutineScope(Dispatchers.IO).launch {
            listEpisodes = (episodeDao.getAllEpisodes()).map {
                mapper.mapEpisodeResultDbForEpisodeResult(it)
            }
        }
        return listEpisodes
    }

    override fun getListCharactersIntoEpisodeDetail(id: String): Observable<List<CharacterResult>> {
        return apiService.getListCharactersIntoEpisodeDetail(id)
    }
}