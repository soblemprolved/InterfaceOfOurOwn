package com.soblemprolved.interfaceofourown.converters.responsebody

import com.soblemprolved.interfaceofourown.service.Login
import okhttp3.ResponseBody
import retrofit2.Converter

object LoginConverter : Converter<ResponseBody, Login> {
    override fun convert(value: ResponseBody): Login = Login
}
