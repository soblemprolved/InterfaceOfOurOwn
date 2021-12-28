package com.soblemprolved.interfaceofourown.service.models

enum class AutocompleteType(
    val pathSegment: String
) {
    TAG("tag"),
    FANDOM("fandom"),
    RELATIONSHIP("relationship"),
    CHARACTER("character"),
    FREEFORM("freeform"),
    PSEUD("pseud");
}

@JvmInline
value class Tag(val tag: String) {
    val urlEncodedTag: String
        get() = encodeTag(tag)

    private fun encodeTag(tag: String): String {
        return tag.replace("/", "*s*")
            .replace("&", "*a*")
            .replace(".", "*d*")
            .replace("?", "*q*")
            .replace("#", "*h*")
    }
}

@JvmInline
value class Csrf(val value: String)

/**
 * This is a helper class that encapsulates the default form parameters required by the login request to AO3.
 * The constructor is internal in order to prevent the end user from instantiating it. This way, it can be
 * used as a default parameter in Retrofit without actually allowing the user to change it.
 */
class LoginFieldMap internal constructor() : AbstractMap<String, String>() {
    override val entries: Set<Map.Entry<String, String>>
        get() {
            val backingSet = mutableSetOf<java.util.AbstractMap.SimpleEntry<String, String>>()
            val addToSet =
                { key: String, value: String -> backingSet.add(java.util.AbstractMap.SimpleEntry(key, value)) }

            addToSet("utf8", "✓")
            addToSet("commit", "Log In")

            return backingSet
        }
}

/**
 * This is a helper class that encapsulates the default form parameters required by the logout request to AO3.
 * The constructor is internal in order to prevent the end user from instantiating it. This way, it can be
 * used as a default parameter in Retrofit without actually allowing the user to change it.
 */
class LogoutFieldMap internal constructor() : AbstractMap<String, String>() {
    override val entries: Set<Map.Entry<String, String>>
        get() {
            val backingSet = mutableSetOf<java.util.AbstractMap.SimpleEntry<String, String>>()
            val addToSet =
                { key: String, value: String -> backingSet.add(java.util.AbstractMap.SimpleEntry(key, value)) }

            addToSet("_method", "delete")

            return backingSet
        }
}

/**
 * This is used to represent a login.
 */
object Login
