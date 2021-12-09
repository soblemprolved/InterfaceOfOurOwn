package com.soblemprolved.orpheus.service.models

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class AO3CallAdapter<T> : CallAdapter<T, AO3Call<T>> {
    override fun responseType(): Type {
        TODO("Not yet implemented")
    }

    override fun adapt(call: Call<T>): AO3Call<T> {
        TODO("Not yet implemented")
    }

}
