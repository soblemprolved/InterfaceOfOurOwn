package com.soblemprolved.orpheus.utils

import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.model.ChallengeType
import com.soblemprolved.orpheus.model.CollectionFilterParameters
import com.soblemprolved.orpheus.model.WorkFilterParameters
import com.soblemprolved.orpheus.service.models.AutocompleteType
import com.soblemprolved.orpheus.service.requests.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

internal fun main(args: Array<String>) {
    val client = OkHttpClient()

    // get response
    val request = CollectionsSearchRequest(
        CollectionFilterParameters(
            collectionChallengeType = ChallengeType.GIFT_EXCHANGE
        ),
        1
    )

    val req = Request.Builder()
        .url(request.url)
        .headers(request.headers)
        .build()

    val response = client.newCall(req).execute()

    val file = File("src/test/resources/responses/collections/page-with-gift-exchange-challenge.in")
    if (!file.exists()) file.createNewFile()

    file.bufferedWriter()
    .use { out ->
        out.write(response.body!!.string())
    }
}
