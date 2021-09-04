package com.abbisea.scottishpowertest.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.abbisea.scottishpowertest.databinding.FragmentAlbumsBinding
import com.abbisea.scottishpowertest.utils.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private val viewModel: AlbumsViewModel by viewModels()
    private val adapter = AlbumsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAlbumsBinding.inflate(inflater, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter { adapter.retry() },
                footer = PagingLoadStateAdapter { adapter.retry() }
            )
            recyclerView.setHasFixedSize(true)
            swipeRefresh.setOnRefreshListener { adapter.refresh() }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                adapter.loadStateFlow.collectLatest {
                    binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                }
            }
            subscribeUi()
            return root
        }
    }

    private fun subscribeUi() {
        viewModel.albums.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }
}