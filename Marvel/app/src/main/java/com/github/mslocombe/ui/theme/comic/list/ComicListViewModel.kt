package com.github.mslocombe.ui.theme.comic.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.mslocombe.paging.Comic
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 100

class ComicListViewModel(
    private val repository: ComicListRepository = ComicListRepository()
) : ViewModel() {

    val items: Flow<PagingData<Comic>> = Pager(
        config = PagingConfig(ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.comicPagingSource() }
    ).flow.cachedIn(viewModelScope)
}