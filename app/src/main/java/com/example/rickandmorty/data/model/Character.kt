package com.example.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "rick_and_morty_character")
data class Character(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String
)


