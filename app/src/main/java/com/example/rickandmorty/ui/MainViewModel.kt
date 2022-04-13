package com.example.rickandmorty.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.repository.Repository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class MainViewModel(private val repository: Repository) : ViewModel() {


    val listCharacter: LiveData<List<Character>>
        get() = _listCharacter

    private val _listCharacter = MutableLiveData<List<Character>>()



    // pagingdata<,>
    val characterPaging = repository.getPagingCharacter().
        // Map outer stream, so you can perform transformations on
        // each paging generation.
    map {
        pagingData->
        pagingData.map {
            Log.d("TAG", "stream of data: $pagingData")
            // Convert items in stream to UiModel.UserModel.
            UiModel.CharacterItem(it)
        }
            .insertSeparators {
            before,after->
            when{
                before==null-> null //UiModel.SeparatorItem("our stream started")
                after == null -> null //UiModel.SeparatorItem("no more data")

                before.character.status == "Alive" -> UiModel.SeparatorItem("Alive")


                // Return null to avoid adding a separator between two items.
                else -> null

            }
        }

    }.cachedIn(viewModelScope).asLiveData()


    fun getCharacter(page: String) = viewModelScope.launch {

        try {
            val response = repository.getCharacter(page)
            Log.d("TAG", "getCharacter: ${response.code()}")
            if (response.isSuccessful)
                _listCharacter.value = response.body()!!.results

            Log.d("TAG", "getCharacter: ${response.body()?.results ?: "not thing"}.value}")
        } catch (e: Exception) {
            _listCharacter.value = emptyList()
        }

    }


    class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java))
                return MainViewModel(repository) as T

            throw IllegalArgumentException("View Model UnKnown")
        }
    }


    sealed class UiModel {
        data class CharacterItem(val character: Character) : UiModel()
        data class SeparatorItem(val description: String) : UiModel()
    }
}