package com.redbox.mirumon.test

import com.redbox.mirumon.BuildConfig.BASE_URL
import com.redbox.mirumon.main.di.modules.networkModule
import com.redbox.mirumon.main.domain.info.DeviceService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class loginTest : KoinTest {

    val service: DeviceService by inject(named("noAuthService"))

    @Before
    fun setup() {
        startKoin {
            modules(networkModule)
        }
    }

    @Test
    fun getToken() = runBlocking {
        val token = service.loginUser("admin2", "123456")
        assertThat(token.accessToken, notNullValue())
        assertThat(token.tokenType, notNullValue())
    }

    @Test
    fun getDevice() = runBlocking {
        val token = "Bearer " + service.loginUser("admin2", "123456").accessToken
        val httpClient = OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder()
                .header(
                    "Authorization",
                    token
                )
                .build()
            return@addInterceptor it.proceed(request)
        }.readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build()
        val service = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(DeviceService::class.java)
        val devices = service.getDevices()
        assertThat(devices, notNullValue())
    }

    @Test
    fun AuthenticatorFail() = runBlocking {
        val devices = service.getDevices()
        assertThat(devices, nullValue())
    }


    @After
    fun after() {
        stopKoin()
    }
}