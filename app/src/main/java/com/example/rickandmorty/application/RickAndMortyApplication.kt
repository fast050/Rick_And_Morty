package com.example.rickandmorty.application

import android.app.Application
import com.example.rickandmorty.data.db.AppDatabase
import com.example.rickandmorty.data.network.RickAndMortyApi
import com.example.rickandmorty.repository.Repository

class RickAndMortyApplication:Application()
{

    val repository by lazy {
        Repository(RickAndMortyApi.retrofitInstance , AppDatabase.getInstance(context = applicationContext)) }


}