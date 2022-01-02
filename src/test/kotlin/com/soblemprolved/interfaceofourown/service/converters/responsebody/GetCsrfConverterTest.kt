package com.soblemprolved.interfaceofourown.service.converters.responsebody

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GetCsrfConverterTest {
    @Test
    fun `Should return the CSRF Token`() {
        val json = File("src/test/resources/responses/csrf/sample-token-dispenser-result.json")
            .readText()
        val responseBody = json.toResponseBody()
        val csrf = GetCsrfConverter.convert(responseBody)
        val actualCsrf = "iY2Nj31QMBZv1oaTsHP2EIJZDzTo4LgW1dNtpDYe9l527X-lDQ1Vz4lmbSOUwAKznr_7-yd-z17DAv9OSPacKQ"
        assertEquals(actualCsrf, csrf.value)
    }
}
