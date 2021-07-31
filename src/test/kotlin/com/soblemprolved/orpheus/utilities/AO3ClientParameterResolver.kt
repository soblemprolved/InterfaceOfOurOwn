package com.soblemprolved.orpheus.utilities

import com.soblemprolved.orpheus.AO3Client
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

// TODO: ensure that this is not instantiated in normal usage
internal object AO3ClientParameterResolver : ParameterResolver {
    private val logging = HttpLoggingInterceptor()
    init {
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val ao3Client = AO3Client(okHttpClient)

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.type == AO3Client::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return ao3Client
    }
}
