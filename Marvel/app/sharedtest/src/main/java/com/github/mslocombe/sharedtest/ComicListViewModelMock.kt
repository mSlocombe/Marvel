package com.github.mslocombe.sharedtest

import androidx.paging.PagingData
import com.github.mslocombe.paging.Comic
import com.github.mslocombe.ui.theme.comic.list.ComicListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ComicListViewModelMock : ComicListViewModel {

    val _items = MutableStateFlow<PagingData<Comic>>(PagingData.empty())
    override val items: Flow<PagingData<Comic>> = _items
}