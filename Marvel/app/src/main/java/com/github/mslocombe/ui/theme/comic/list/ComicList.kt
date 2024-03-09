package com.github.mslocombe.ui.theme.comic.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.github.mslocombe.paging.Comic

@Composable
fun ComicList(
    comics: LazyPagingItems<Comic>
) {
    var largeImage by remember { mutableStateOf<Comic?>(null) }

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
                onClick = {
                    item?.let { largeImage = it }
                }
            )
        }
    }

    largeImage?.let {
        ComicModal(
            it.title,
            it.description,
            it.thumbnailUrl
        ) {
            largeImage = null
        }
    }
}