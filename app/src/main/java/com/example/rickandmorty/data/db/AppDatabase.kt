package com.example.rickandmorty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.model.Character


@Database(version = 1, entities = [Character::class, RemoteKeys::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRemoteKeyDao(): RemoteKeyDao
    abstract fun getCharacterDao(): CharacterDao

    companion object {

        private const val Character_DB = "RickAndMorty.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
               val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, Character_DB)
                .build()
    }

}
