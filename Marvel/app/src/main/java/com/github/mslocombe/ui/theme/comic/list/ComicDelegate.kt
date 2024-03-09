package com.github.mslocombe.ui.theme.comic.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.github.mslocombe.R

@Composable
fun ComicDelegate(
    modifier: Modifier = Modifier,
    title: String,
    thumbnailUrl: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        content = {
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .heightIn(max = 100.dp)
                        .widthIn(max = 100.dp),
                    model = thumbnailUrl,
                    contentDescription = null,
                    loading = {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    },
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.kodemono_bold))
                )
            }
        }
    )
}

@Preview
@Composable
fun ComicPreview() {
    ComicDelegate(
        title = "Preview Comic (2024)",
        thumbnailUrl = null,
        onClick = {}
    )
}