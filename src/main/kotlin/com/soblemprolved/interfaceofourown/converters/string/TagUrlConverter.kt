package com.soblemprolved.interfaceofourown.converters.string

import com.soblemprolved.interfaceofourown.model.Tag
import retrofit2.Converter

/**
 * Converts [Tag]s to their special URL-encoded representation when passed as a retrofit parameter.
 * This only applies when browsing under tags (i.e. https://archiveofourown.org/tags/${ENCODED_TAG})
 */
internal object TagUrlConverter : Converter<Tag, String> {
    override fun convert(value: Tag): String = value.urlEncodedTag
}
