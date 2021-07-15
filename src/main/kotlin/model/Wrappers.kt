package model

/**
 * Represents a block of HTML code.
 * @property data The block of HTML code.
 */
data class Html(
    val data: String
)

/**
 * Represents a block of CSS code.
 * @property data The block of CSS code.
 */
data class Css(
    val data: String
)

/**
 * Represents the main tag, which is to be encoded before being used to make a work filter request.
 * @property name The name of the Tag.
 */
data class MainTag(
    val name: String
)
