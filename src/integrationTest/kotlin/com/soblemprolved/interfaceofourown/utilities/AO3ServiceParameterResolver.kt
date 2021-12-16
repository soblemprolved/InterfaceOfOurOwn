package com.soblemprolved.interfaceofourown.utilities

import com.soblemprolved.interfaceofourown.service.AO3Service
import com.soblemprolved.interfaceofourown.service.converters.AO3ConverterFactory
import com.soblemprolved.interfaceofourown.service.models.AO3ResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import retrofit2.Retrofit

internal object AO3ServiceParameterResolver : ParameterResolver {
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .followRedirects(false)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://archiveofourown.org/")
        .client(client)
        .addConverterFactory(AO3ConverterFactory())
        .addCallAdapterFactory(AO3ResponseCallAdapterFactory())
        .build()

    private val service = retrofit.create(AO3Service::class.java)

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.type == AO3Service::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return service
    }
}
