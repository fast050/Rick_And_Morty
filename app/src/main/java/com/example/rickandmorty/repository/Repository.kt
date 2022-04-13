package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.rickandmorty.data.CharactersMediator
import com.example.rickandmorty.data.db.AppDatabase
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.network.RickAndMortyPagingSource
import com.example.rickandmorty.data.network.RickAndMortyService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class Repository(private val service:RickAndMortyService,private val database:AppDatabase)
{
    suspend fun getCharacter(page:String):Response<CharacterResponse>
    {
        return service.getCharacter(page)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPagingCharacter(): Flow<PagingData<Character>>
    {
        val databasePagingSource = { database.getCharacterDao().getCharacters() }

        return Pager(config = PagingConfig(
            pageSize = 20
            , enablePlaceholders = false,
            maxSize = 100)
            , remoteMediator = CharactersMediator(service,database)
                      ,pagingSourceFactory = databasePagingSource
        ).flow
    }


}