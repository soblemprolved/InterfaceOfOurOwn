package com.soblemprolved.interfaceofourown.converters.responsebody

import com.soblemprolved.interfaceofourown.service.Logout
import okhttp3.ResponseBody
import retrofit2.Converter

internal object LogoutConverter : Converter<ResponseBody, Logout> {
    override fun convert(value: ResponseBody): Logout = Logout
}
