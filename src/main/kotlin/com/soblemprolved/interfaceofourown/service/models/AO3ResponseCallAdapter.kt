package com.soblemprolved.interfaceofourown.service.models

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class AO3ResponseCallAdapter(private val type: Type) : CallAdapter<Type, Call<AO3Response<Type>>> {
    override fun responseType(): Type = type
    override fun adapt(call: Call<Type>): Call<AO3Response<Type>> = AO3Call(call)
}
