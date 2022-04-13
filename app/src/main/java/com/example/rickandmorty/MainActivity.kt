package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmorty.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val storage = getPreferences( MODE_PRIVATE)

        storage.edit().putInt("number",5).apply()

        val storageLevelApp = getSharedPreferences("khalid" , MODE_PRIVATE)

    }

}