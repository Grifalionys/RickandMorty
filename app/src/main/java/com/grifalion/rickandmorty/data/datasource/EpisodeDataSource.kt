package com.grifalion.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grifalion.rickandmorty.data.api.RetrofitInstance
import com.grifalion.rickandmorty.domain.models.episode.Episode

class EpisodeDataSource(private val name: String, private val episode: String): PagingSource<Int, Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = RetrofitInstance.getInstance()
            val responseData = mutableListOf<Episode>()
            responseData.addAll(response.getEpisodes(currentLoadingPageKey, name,episode).body()!!.results)

            val prevKey = if(currentLoadingPageKey == 1){
                null
            } else {
                currentLoadingPageKey - 1
            }
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}