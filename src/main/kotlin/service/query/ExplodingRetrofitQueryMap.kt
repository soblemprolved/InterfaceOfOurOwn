package service.query

import java.util.AbstractMap.SimpleEntry

/**
 * Allows for queries to be repeated (i.e. exploded) when used as a Retrofit QueryMap.
 * Original code from user fmmr on Github: https://github.com/square/retrofit/issues/1324
 */
open class ExplodingRetrofitQueryMap(
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
