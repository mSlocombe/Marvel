package com.github.mslocombe.ui.theme.comic.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
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

    Dialog(
        onDismissRequest = onDismissed,
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = true)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            Brush.linearGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.White.copy(alpha = 0.5f)
                                ),
                                start = Offset(
                                    Float.POSITIVE_INFINITY / 2,
                                    Float.POSITIVE_INFINITY
                                ),
                                end = Offset(Float.POSITIVE_INFINITY / 2, 0f),
                            ),
                        )
                    },
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = """
                    $title
                    
                    $description
                """.trimIndent(),
                color = Color.Green,
                fontFamily = FontFamily(Font(R.font.kodemono_bold)),
                fontSize = 24.sp
            )
        }
    }
}

@PreviewScreenSizes
@Composable
fun ModalPreview() {
    ComicModal(
        title = "Preview Title",
        description = "Preview Description",
        imageUrl = "", {})
}