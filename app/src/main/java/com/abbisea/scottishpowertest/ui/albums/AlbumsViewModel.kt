package com.abbisea.scottishpowertest.ui.albums

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeFeedViewModel @Inject constructor(
) : ViewModel()