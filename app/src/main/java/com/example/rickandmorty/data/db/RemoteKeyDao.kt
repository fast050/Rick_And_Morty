package com.example.rickandmorty.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>)


    @Query("Select * from remotekeys where repoId=:remoteKeyId")
    suspend fun getRemoteKeyById(remoteKeyId:Int):RemoteKeys

    @Query("Delete from remotekeys")
    suspend fun clearRemoteKeys()

}