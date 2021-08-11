package com.soblemprolved.orpheus.service.converters

import okhttp3.Response

/**
 * If the logout is successful, return true.
 * I don't know if the logout can ever be unsuccessful?
 */
internal object LogoutConverter : Converter<Boolean> {
    override fun convert(response: Response): Boolean {
        when (response.code) {
            302 -> TODO()
            else -> Converter.handleResponseCode(response.code)
        }
        TODO("Not implemented yet")
    }
}
