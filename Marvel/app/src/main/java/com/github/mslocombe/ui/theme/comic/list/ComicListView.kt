package com.github.mslocombe.ui.theme.comic.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun ComicListView(
    viewModel: ComicListViewModel = viewModel()
) {
    val comics = viewModel.items.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(comics.itemCount) { index ->
            val item = comics[index]
            ComicDelegate(
                modifier = Modifier.fillMaxWidth(),
                title = item?.title ?: "Missing Title",
                thumbnailUrl = item?.thumbnailUrl,
//                onClick = {}
            )
        }
    }
}