package com.soblemprolved.orpheus.service.old.converters

import okhttp3.Response

/**
 * Parses the response. If the login is successful, return
 */
internal object LoginConverter : Converter<Boolean> {
    override fun convert(response: Response): Boolean {
        when (response.code) {
            302 -> TODO("success or already logged in (failure)")
            200 -> TODO("failure")
        }
        TODO("Not implemented yet")

        // if 302 to profile page, then login works
        // else if 200 then it dont work
        // refer to ao3-api for more deets
    }
}
