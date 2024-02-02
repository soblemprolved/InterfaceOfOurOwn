package com.soblemprolved.interfaceofourown.service

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * A simple call adapter to allow AO3Responses as wrappers for return types.
 */
internal class AO3ResponseCallAdapter(private val type: Type) : CallAdapter<Type, Call<AO3Response<Type>>> {
    override fun responseType(): Type = type
    override fun adapt(call: Call<Type>): Call<AO3Response<Type>> = when (type) {
        Login::class.java -> AO3LoginCall(call as Call<Login>) as Call<AO3Response<Type>>
        Logout::class.java -> AO3LogoutCall(call as Call<Logout>) as Call<AO3Response<Type>>
        else -> AO3Call(call)
    }
}
