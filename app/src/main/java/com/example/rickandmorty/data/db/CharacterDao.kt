package com.example.rickandmorty.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.model.Character

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(remoteKeys: List<Character>)

    @Query("SELECT * FROM rick_and_morty_character")
    fun getCharacters():PagingSource<Int,Character>

    @Query("Delete from rick_and_morty_character")
    suspend fun clearCharacters()
}