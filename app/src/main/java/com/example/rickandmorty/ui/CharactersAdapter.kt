package com.example.rickandmorty.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.RickAndMortyItemBinding
import com.example.rickandmorty.databinding.SeparatorViewItemBinding
import com.example.rickandmorty.ui.MainViewModel.UiModel

class CharactersAdapter: PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when(viewType){
           TYPE_CHARACTER ->  RickAndMortyListViewHolder.create(parent)
           TYPE_SEPARATOR -> SeparatorViewHolder.create(parent)
           else -> throw UnsupportedOperationException("Unknown view")
       }
     }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val uiModel = getItem(position)

        when(uiModel){
            is UiModel.CharacterItem -> (holder as RickAndMortyListViewHolder).bind(uiModel.character)
            is UiModel.SeparatorItem ->(holder as SeparatorViewHolder).bind(uiModel.description)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.CharacterItem -> TYPE_CHARACTER
            is UiModel.SeparatorItem -> TYPE_SEPARATOR
            else -> TYPE_UNKNOWN
        }
    }


   class RickAndMortyListViewHolder(private val binding:RickAndMortyItemBinding):RecyclerView.ViewHolder(binding.root){

       init {
           binding.root.setOnClickListener {
               Log.d("TAG", "$bindingAdapterPosition: ")
               Toast.makeText(binding.root.context,bindingAdapterPosition.toString(),Toast.LENGTH_SHORT).show()
           }
       }
        fun bind(character: Character)
        {
            binding.apply {
                characterGender.text = character.gender
                characterName.text = character.name
                Glide.with(binding.root).load(character.image).into(characterImage)

            }

        }

        companion object {


            fun create(parent: ViewGroup): RickAndMortyListViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rick_and_morty_item, parent, false)
                val binding = RickAndMortyItemBinding.bind(view)
                return RickAndMortyListViewHolder(binding)
            }
        }

    }

    class SeparatorViewHolder(val binding: SeparatorViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //private val description: TextView = view.findViewById(R.id.separator_description)

        fun bind(separatorText: String) {
           binding.description.text = separatorText
        }

        companion object {
            fun create(parent: ViewGroup): SeparatorViewHolder {
                val layoutInflater =  LayoutInflater.from(parent.context)
               val binding = SeparatorViewItemBinding.inflate(layoutInflater, parent, false)
                return SeparatorViewHolder(binding)
            }
        }
    }


    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: MainViewModel.UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.CharacterItem && newItem is UiModel.CharacterItem &&
                        oldItem.character.id == newItem.character.id) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }

        private const val TYPE_CHARACTER = 0
        private const val TYPE_SEPARATOR = 1
        private const val TYPE_UNKNOWN = 3


    }


   }
