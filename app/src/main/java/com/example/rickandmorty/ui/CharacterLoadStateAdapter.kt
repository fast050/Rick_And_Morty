package com.example.rickandmorty.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ListLoadStateFooterViewItemBinding
import com.example.rickandmorty.ui.CharacterLoadStateAdapter.CharacterLoadStateViewHolder.Companion.create

class CharacterLoadStateAdapter(val retry: () -> Unit):
    LoadStateAdapter<CharacterLoadStateAdapter.CharacterLoadStateViewHolder>()
{

    class CharacterLoadStateViewHolder(
        private val binding: ListLoadStateFooterViewItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage

                Log.d("TAG", "bind: ${loadState.error.cause}")
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error

        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): CharacterLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_load_state_footer_view_item, parent, false)
                val binding = ListLoadStateFooterViewItemBinding.bind(view)
                return CharacterLoadStateViewHolder(binding, retry)
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState){
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharacterLoadStateViewHolder {
      return create(parent,retry)
    }

}