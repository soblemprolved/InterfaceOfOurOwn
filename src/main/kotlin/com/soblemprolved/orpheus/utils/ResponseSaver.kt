package com.soblemprolved.orpheus.utils

import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.model.WorkFilterParameters
import com.soblemprolved.orpheus.service.models.AutocompleteType
import com.soblemprolved.orpheus.service.requests.AutocompleteRequest
import com.soblemprolved.orpheus.service.requests.BookmarksByTagRequest
import com.soblemprolved.orpheus.service.requests.WorkRequest
import com.soblemprolved.orpheus.service.requests.WorksByTagRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

internal fun main(args: Array<String>) {
    val client = OkHttpClient()

    // get response
    val request = AutocompleteRequest.withDefaultConverter(AutocompleteType.TAG, "fyjtfhfv")

    val req = Request.Builder()
        .url(request.url)
        .headers(request.headers)
        .build()

    val response = client.newCall(req).execute()

    val file = File("src/test/resources/responses/autocomplete/no-matching-results.in")
    if (!file.exists()) file.createNewFile()

    file.bufferedWriter()
    .use { out ->
        out.write(response.body!!.string())
    }
}
