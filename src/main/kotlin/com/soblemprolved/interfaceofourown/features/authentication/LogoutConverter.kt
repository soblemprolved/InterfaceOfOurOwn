package com.soblemprolved.interfaceofourown.features.authentication

import okhttp3.ResponseBody
import retrofit2.Converter

internal object LogoutConverter : Converter<ResponseBody, Logout> {
    override fun convert(value: ResponseBody): Logout = Logout
}
