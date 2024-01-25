package com.soblemprolved.interfaceofourown.model

/*
These wrappers are represented as inline classes for the user, so that they will not confuse them
with ordinary Strings. Their usage is different enough that they warrant inline classes.
 */

/**
 * Represents a block of HTML code.
 * @property data The block of HTML code.
 */
@JvmInline
value class Html(val data: String)

/**
 * Represents a block of CSS code.
 * @property data The block of CSS code.
 */
@JvmInline
value class Css(val data: String)

class Tag(val tag: String) {
    val urlEncodedTag: String
        get() = encodeTag(tag)

    private fun encodeTag(tag: String): String {
        return tag.replace("/", "*s*")
            .replace("&", "*a*")
            .replace(".", "*d*")
            .replace("?", "*q*")
            .replace("#", "*h*")
    }

    override fun toString(): String = urlEncodedTag
}

/**
 * Represents a CSRF token.
 */
class Csrf(
    /**
     * The string literal of the CSRF token.
     */
    val value: String
)
