package com.github.mslocombe.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.mslocombe.marvelapi.ImageSize
import com.github.mslocombe.ui.theme.comic.list.generateAuth
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.math.max

private const val STARTING_KEY = 0

class ComicPagingSource(
    private val httpClient: HttpClient = HttpClient(CIO)
) : PagingSource<Int, Comic>() {
    override fun getRefreshKey(state: PagingState<Int, Comic>): Int {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
//        val anchorPosition = state.anchorPosition ?: return null
//        val comic = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = 0)
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comic> {
        val start = params.key ?: STARTING_KEY
        val end = start + params.loadSize

        return LoadResult.Page(
            data = parseComics(getInitialComics(start)),
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = start - params.loadSize)
            },
            nextKey = end
        )
    }

    private suspend fun getInitialComics(offset: Int) = withContext(Dispatchers.IO) {
        val hash = generateAuth()
        val paramResult = httpClient.get("https://gateway.marvel.com/v1/public/comics") {
            url {
                with(parameters) {
                    append("offset", offset.toString())
                    append("limit", "100")
                    append("ts", hash.timestamp.toString())
                    append("apikey", "0d80096c942acc5a31f45594718ef72d")
                    append("hash", hash.hash)
                }
            }
        }

        paramResult.bodyAsText()
    }

    private fun parseComics(body: String): List<Comic> {
        val jsonBody = JSONObject(body)
        val data = jsonBody.getJSONObject("data")
        val results = data.getJSONArray("results")
        return (0..<results.length()).map { index ->
            val thisComic = results.getJSONObject(index)
            val thumbnail = thisComic.getJSONObject("thumbnail")
            val thumbnailPath = "${thumbnail.getString("path")}/${ImageSize.STANDARD_MEDIUM.key}.${
                thumbnail.getString("extension")
            }"
            Comic(
                thisComic.getString("title"),
                thumbnailPath
            )
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

}