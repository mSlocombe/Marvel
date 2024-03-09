package com.github.mslocombe

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.mslocombe.sharedtest.ComicListViewModelMock
import com.github.mslocombe.ui.theme.comic.list.ComicListView
import org.junit.Rule
import org.junit.Test

class ComicListViewTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun loadingCircleShownOnEntry() {
        val viewModel = ComicListViewModelMock()
        compose.setContent {
            ComicListView(viewModel)
        }

        compose.onNodeWithTag("ComicListLoading").assertIsDisplayed()
    }
}