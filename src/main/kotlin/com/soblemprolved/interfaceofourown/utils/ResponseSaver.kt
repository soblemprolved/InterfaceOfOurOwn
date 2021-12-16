package com.soblemprolved.interfaceofourown.utils

import okhttp3.OkHttpClient

internal fun main(args: Array<String>) {
    val client = OkHttpClient()

    // get response
//    val request = CollectionsSearchRequest(
//        CollectionFilterParameters(
//            collectionChallengeType = ChallengeType.GIFT_EXCHANGE
//        ),
//        1
//    )
//
//    val req = Request.Builder()
//        .url(request.url)
//        .headers(request.headers)
//        .build()
//
//    val response = client.newCall(req).execute()
//
//    val file = File("src/test/resources/responses/collections/page-with-gift-exchange-challenge.in")
//    if (!file.exists()) file.createNewFile()
//
//    file.bufferedWriter()
//    .use { out ->
//        out.write(response.body!!.string())
//    }
}
