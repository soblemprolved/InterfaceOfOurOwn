package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.service.models.AO3Response
import com.soblemprolved.interfaceofourown.service.models.Csrf
import com.soblemprolved.interfaceofourown.service.models.Login
import com.soblemprolved.interfaceofourown.service.models.Logout
import com.soblemprolved.interfaceofourown.utilities.AO3ServiceParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ServiceParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LoginLogoutTest(private val service: AO3Service) {
    val username = System.getenv("AO3_USERNAME")
    val password = System.getenv("AO3_PASSWORD")

    init {
        assertNotNull(username)
        assertNotNull(password)
    }

    @Test
    fun `Should log in and log out successfully`() {
        val csrf = runBlocking { service.getCsrfToken() }
        assertTrue(csrf is AO3Response.Success<Csrf>)
        val token = (csrf as AO3Response.Success).value
        val login = runBlocking { service.login(username, password, token) }
        assertTrue(login is AO3Response.Success<Login>)

        val csrf2 = runBlocking { service.getCsrfToken() }
        assertTrue(csrf2 is AO3Response.Success<Csrf>)
        val token2 = (csrf2 as AO3Response.Success).value
        val logout = runBlocking { service.logout(token2) }
        print(logout)
        assertTrue(logout is AO3Response.Success<Logout>)
    }
}
