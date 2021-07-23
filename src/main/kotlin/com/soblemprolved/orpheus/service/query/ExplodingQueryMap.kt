package com.soblemprolved.orpheus.service.query

import java.util.AbstractMap.SimpleEntry

/**
 * Forces Retrofit to generate separate parameters for each item in the value-list (i.e. exploding) for every entry in
 * the [original], when an [ExplodingQueryMap] object is used as a Retrofit QueryMap.
 *
 * Original code from user fmmr on Github: https://github.com/square/retrofit/issues/1324
 */
open class ExplodingQueryMap(
    private val original: Map<String, List<String>>
) : AbstractMap<String, String>() {
    override val entries: Set<Map.Entry<String, String>>
        get() {
            return original.entries
                .flatMap { (key, value) ->
                    value.map { SimpleEntry(key, it) }
                }
                .toSet()
        }
}
