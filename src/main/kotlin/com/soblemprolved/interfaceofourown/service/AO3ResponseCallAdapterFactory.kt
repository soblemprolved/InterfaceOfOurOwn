package com.soblemprolved.interfaceofourown.service

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A [CallAdapter.Factory] to create [AO3ResponseCallAdapter] instances.
 */
class AO3ResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                AO3Response::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    AO3ResponseCallAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}
