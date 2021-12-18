package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.service.converters.AO3ConverterFactory
import com.soblemprolved.interfaceofourown.service.models.AO3ResponseCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Factory method to create an instance of an [AO3Service].
 *
 * Specify the [baseUrl] if you wish to use this to perform testing.
 *
 * Specify the [okHttpClient] if you have an existing [OkHttpClient] in your application. Retrofit will share resources
 * with the existing client, but it will use its own configuration for its own requests.
 */
fun AO3Service.create(
    baseUrl: String = "https://archiveofourown.org/",
    okHttpClient: OkHttpClient? = null,
    interceptors: List<Interceptor> = listOf(),
    converterFactories: List<Converter.Factory> = listOf(),
    callAdapterFactories: List<CallAdapter.Factory> = listOf()
) : AO3Service {
    val client = if (okHttpClient == null) {
        OkHttpClient.Builder()
            .apply {
                for (interceptor in interceptors) {
                    this.addInterceptor(interceptor)
                }
            }
            .followRedirects(false)
            .build()
    } else {
        okHttpClient.newBuilder()
            .apply {
                for (interceptor in interceptors) {
                    this.addInterceptor(interceptor)
                }
            }
            .followRedirects(false)
            .build()
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(AO3ConverterFactory())
        .addCallAdapterFactory(AO3ResponseCallAdapterFactory())
        .apply {
            for (converterFactory in converterFactories) {
                this.addConverterFactory(converterFactory)
            }
        }
        .apply {
            for (callAdapterFactory in callAdapterFactories) {
                this.addCallAdapterFactory(callAdapterFactory)
            }
        }
        .build()

    return retrofit.create(AO3Service::class.java) // TODO: should I also return the retrofit and okhttp client?
}
