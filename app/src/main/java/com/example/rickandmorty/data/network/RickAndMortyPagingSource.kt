package com.example.rickandmorty.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.Character
import retrofit2.HttpException
import java.io.IOException

class RickAndMortyPagingSource(private val service: RickAndMortyService) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {



        val currentPagingIndex = params.key ?: START_PAGE_INDEX
        Log.d("TAG", "load =========================: $currentPagingIndex ------${params.loadSize}")
        return try {

            val response = service.getCharacter(currentPagingIndex.toString())

            val data = response.body()?.results
            val next = if (response.body()?.info?.next != null) currentPagingIndex.plus(1) else null
            val prev = if (currentPagingIndex == START_PAGE_INDEX)
                null
            else currentPagingIndex.minus(1)

            LoadResult.Page(data = data.orEmpty(), nextKey = next, prevKey = prev)
        }
            catch (e: IOException) {
                // IOException for network failures.
                 LoadResult.Error(e)
            } catch (e: HttpException) {
                // HttpException for any non-2xx HTTP status codes.
                 LoadResult.Error(e)
            }
    }

    companion object {
        const val START_PAGE_INDEX = 1
        const val TAG ="RickAndMortyPagingSource"
    }
}