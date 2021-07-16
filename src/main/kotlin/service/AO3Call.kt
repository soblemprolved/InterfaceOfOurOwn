package service

import okhttp3.Call
import service.converters.Converter
import service.models.AO3Error
import service.models.AO3Response
import java.io.IOException

class AO3Call<T>(
    val call: Call,
    val converter: Converter<T>
) {
    fun execute(): AO3Response<T> {
        try {
            val response = call.execute()
            return AO3Response.Success(converter.convert(response))
        } catch (e: IOException) {
            return AO3Response.NetworkError
        } catch (e: AO3Error) {
            return AO3Response.ServerError(e)
        }
    }
}