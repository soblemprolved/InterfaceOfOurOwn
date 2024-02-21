package com.soblemprolved.interfaceofourown.features.authentication

import okhttp3.ResponseBody
import retrofit2.Converter

internal object LoginConverter : Converter<ResponseBody, Login> {
    override fun convert(value: ResponseBody): Login = Login
}
