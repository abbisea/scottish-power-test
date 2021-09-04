package com.abbisea.scottishpowertest.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abbisea.scottishpowertest.R
import kotlinx.android.synthetic.main.item_loading_state.view.*

class PagingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val progress = holder.itemView.load_state_progress
        val btnRetry = holder.itemView.load_state_retry
        val txtErrorMessage = holder.itemView.load_state_errorMessage

        progress.isVisible = loadState is LoadState.Loading
        btnRetry.isVisible = loadState is LoadState.Error
        txtErrorMessage.isVisible = loadState is LoadState.Error

        btnRetry.setOnClickListener {
            retry.invoke()
        }
        txtErrorMessage.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading_state, parent, false)
        )

    class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view)
}