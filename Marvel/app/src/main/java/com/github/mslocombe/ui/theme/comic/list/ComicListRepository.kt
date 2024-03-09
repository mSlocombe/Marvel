package com.github.mslocombe.ui.theme.comic.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.mslocombe.BuildConfig
import com.github.mslocombe.marvelapi.ImageSize
import com.github.mslocombe.paging.Comic
import com.github.mslocombe.paging.MarvelApi
import org.json.JSONObject
import java.security.MessageDigest
import kotlin.math.max

private const val STARTING_KEY = 0

class ComicListRepository(
    private val marvelApi: MarvelApi = MarvelApi()
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
            data = parseComics(marvelApi.getInitialComics(start)),
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = start - params.loadSize)
            },
            nextKey = end
        )
    }

    private fun parseComics(body: String): List<Comic> {
        val jsonBody = JSONObject(body)
        val data = jsonBody.getJSONObject("data")
        val results = data.getJSONArray("results")
        return (0..<results.length()).map { index ->
            val thisComic = results.getJSONObject(index)
            val thumbnail = thisComic.getJSONObject("thumbnail")
            val thumbnailPath = "${thumbnail.getString("path")}/${ImageSize.PORTRAIT_216_324.key}.${
                thumbnail.getString("extension")
            }"
            Comic(
                thisComic.getString("title"),
                thisComic.getString("description"),
                thumbnailPath
            )
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}

data class RequestAuth(
    val timestamp: Long,
    val hash: String
)

@OptIn(ExperimentalStdlibApi::class)
fun generateAuth(): RequestAuth {
    // md5(ts+privateKey+publicKey)
    val ts = System.currentTimeMillis()
    val private = BuildConfig.marvel_private_api_key
    val public = BuildConfig.marvel_public_api_key
    val messageDigest = MessageDigest.getInstance("MD5")
    val combined = "${ts}${private}${public}"
    val digest = messageDigest.digest(combined.toByteArray())
    return RequestAuth(ts, digest.toHexString())
}
