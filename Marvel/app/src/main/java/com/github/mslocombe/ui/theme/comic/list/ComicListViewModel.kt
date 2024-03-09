package com.github.mslocombe.ui.theme.comic.list

import androidx.paging.PagingData
import com.github.mslocombe.paging.Comic
import kotlinx.coroutines.flow.Flow

interface ComicListViewModel {

    val items: Flow<PagingData<Comic>>
}