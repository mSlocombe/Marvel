package com.github.mslocombe.ui.theme.comic.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun ComicListView(
    modifier: Modifier = Modifier,
    viewModel: ComicListViewModel = viewModel(ComicListViewModelImpl::class.java)
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        val comics = viewModel.items.collectAsLazyPagingItems()

        val hasContent by remember {
            derivedStateOf {
                comics.itemCount > 0
            }
        }

        if (!hasContent) {
            ComicListLoading()
        } else {
            ComicList(comics)
        }
    }
}