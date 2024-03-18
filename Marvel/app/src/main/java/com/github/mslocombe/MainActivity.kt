package com.github.mslocombe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.github.mslocombe.ui.theme.MarvelTheme
import com.github.mslocombe.ui.theme.comic.list.ComicListView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "comic/list") {
                    composable(route = "comic/list") {
                        Column(modifier = Modifier.fillMaxSize()) {
                            ComicListView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f, true)
                            )
                            AttributionText()
                        }
                    }

                    composable(route = "comic/detail/{comic_id}") {
                        val id = URLDecoder.decode(
                            it.arguments?.getString("comic_id"),
                            StandardCharsets.UTF_8.toString()
                        )

                        SubcomposeAsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = id,
                            contentDescription = null,
                            loading = {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            },
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AttributionText() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        text = "Data provided by Marvel. © 2014 Marvel"
    )
}