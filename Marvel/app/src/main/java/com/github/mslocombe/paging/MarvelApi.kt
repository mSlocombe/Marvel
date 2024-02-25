package com.github.mslocombe.paging

import com.github.mslocombe.ui.theme.comic.list.generateAuth
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MarvelApi(
    private val httpClient: HttpClient = HttpClient(CIO)
) {
    suspend fun getInitialComics(offset: Int) = withContext(Dispatchers.IO) {
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
}