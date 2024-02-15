package com.github.mslocombe.ui.theme.comic.list

import com.github.mslocombe.paging.ComicPagingSource
import java.security.MessageDigest

class ComicListRepository(
    private val pagingSource: ComicPagingSource = ComicPagingSource()
) {
    fun comicPagingSource() = pagingSource
}

data class RequestAuth(
    val timestamp: Long,
    val hash: String
)

@OptIn(ExperimentalStdlibApi::class)
fun generateAuth(): RequestAuth {
    // md5(ts+privateKey+publicKey)
    val ts = System.currentTimeMillis()
    val private = "f4b781590ae1b6d776c1164df18b02e6e8b53966"
    val public = "0d80096c942acc5a31f45594718ef72d"
    val messageDigest = MessageDigest.getInstance("MD5")
    val combined = "${ts}${private}${public}"
    val digest = messageDigest.digest(combined.toByteArray())
    return RequestAuth(ts, digest.toHexString())
}
