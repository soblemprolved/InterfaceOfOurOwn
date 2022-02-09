package com.soblemprolved.interfaceofourown.service

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
 * A dummy object that represents a Login.
 */
object Login

/**
 * A dummy object that represents a Logout.
 */
object Logout
