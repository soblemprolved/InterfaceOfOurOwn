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
