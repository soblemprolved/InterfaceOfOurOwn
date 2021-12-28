package com.soblemprolved.interfaceofourown.service.converters.responsebody

import com.soblemprolved.interfaceofourown.service.models.Logout
import okhttp3.ResponseBody
import retrofit2.Converter

object LogoutConverter : Converter<ResponseBody, Logout> {
    override fun convert(value: ResponseBody): Logout = Logout
}
