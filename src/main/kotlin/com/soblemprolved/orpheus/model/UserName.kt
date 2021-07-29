package com.soblemprolved.orpheus.model

/**
 * Represents a user in AO3.
 *
 * Users who are active as themselves (i.e. their display names do not have brackets) should pass in null
 * as a pseudonym.
 *
 * @property username Represents the username of the [UserName] if the [UserName] is a registered Archive member. If the [UserName]
 * is not registered (i.e. the [UserName] is anonymous), then this property represents the name used by the
 * unregistered [UserName].
 *
 * @property pseudonym Represents the pseudonym used by the [UserName] at the time of the activity in question
 * (e.g. posting works, commenting, viewing a pseud profile).
 *
 * This property should be null if the [UserName] is not using
 * a pseudonym (i.e. when the name displayed on AO3 does not have brackets), or if the user is an anonymous user.
 *
 * @property isRegisteredUser True if the user is registered, false if the user is not registered (i.e. anonymous).
 */
data class UserName(
    val username: String,
    val pseudonym: String?,
    val isRegisteredUser: Boolean
) {
    init {
        require(isRegisteredUser || pseudonym == null) {
            "Pseudonym cannot be null when the User is not a registered user"
        }
    }

    val displayName = "$username${if (pseudonym != null) " ($pseudonym)" else ""}"

    companion object {
        /**
         * Creates a registered [UserName] if the display name is associated with a URL, otherwise creates an
         * unregistered [UserName].
         */
        fun from(displayName: String, hasUrl: Boolean): UserName {
            if (hasUrl) {
                // this user is registered, hence the username follows AO3's format.
                val tokens = displayName.removeSuffix(")").split(" (")

                if (tokens.size > 2 || tokens.size == 0) {
                    TODO("throw an exception")
                }

                return UserName(
                    username = tokens[0],
                    pseudonym = if (tokens.size == 2) tokens[1] else null,
                    isRegisteredUser = true
                )
            } else {
                // this user is not registered, hence the username *might* not follow AO3's format.
                return UserName(
                    username = displayName,
                    pseudonym = null,
                    isRegisteredUser = false
                )
            }
        }
    }
}
