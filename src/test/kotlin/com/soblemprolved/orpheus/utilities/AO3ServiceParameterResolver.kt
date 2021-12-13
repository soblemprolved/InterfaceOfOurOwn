package com.soblemprolved.orpheus.utilities

import com.soblemprolved.orpheus.service.AO3Service
import com.soblemprolved.orpheus.service.converters.AO3ConverterFactory
import com.soblemprolved.orpheus.service.models.AO3CallAdapterFactory
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import retrofit2.Retrofit

object AO3ServiceParameterResolver : ParameterResolver {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://archiveofourown.org/")
        .addConverterFactory(AO3ConverterFactory())
        .addCallAdapterFactory(AO3CallAdapterFactory())
        .build()
    private val service = retrofit.create(AO3Service::class.java)

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.type == AO3Service::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return service
    }
}
