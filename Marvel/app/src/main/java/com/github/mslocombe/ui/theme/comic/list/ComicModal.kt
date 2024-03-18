package com.github.mslocombe.ui.theme.comic.list

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.mslocombe.R
@Composable
fun ComicModal(
    title: String,
    description: String,
    imageUrl: String,
    onDismissed: () -> Unit
) {
    val context = LocalContext.current

    Box(
        Modifier.fillMaxSize().background(Color(0xFFD9D9D9))
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = description,
                    fontFamily = FontFamily(Font(R.font.zen_dots_regular)),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        ComicDetailTopBar(title, onDismissed)
    }
}

@Composable
fun ComicDetailTopBar(
    comicTitle: String,
    onCloseClicked: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0f),
                        Color.Black.copy(alpha = 1f)
                    ),
                    start = Offset(
                        Float.POSITIVE_INFINITY / 2,
                        Float.POSITIVE_INFINITY
                    ),
                    end = Offset(Float.POSITIVE_INFINITY / 2, 0f),
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ComicTitle(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f, false),
            title = comicTitle
        )
        ComicDetailClose(onCloseClicked)
    }
}

@Composable
fun ComicTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily(Font(R.font.zen_dots_regular))
    )
}

@Composable
fun ComicDetailClose(
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    IconButton(
        interactionSource = interactionSource,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.cancel_fill0_wght400_grad0_opsz24),
                contentDescription = stringResource(id = R.string.close_detail),
                tint = Color.White
            )
        }
    )
}

@Preview
@Composable
fun ModalPreview() {
    ComicModal(
        title = LoremIpsum(20).values.joinToString(),
        description = LoremIpsum(35).values.joinToString(),
        imageUrl = "", {})
}