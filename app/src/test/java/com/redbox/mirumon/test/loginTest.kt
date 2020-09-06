package com.redbox.mirumon.test


import com.redbox.mirumon.BuildConfig.BASE_URL
import com.redbox.mirumon.main.di.modules.testModule
import com.redbox.mirumon.main.domain.info.DeviceService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class loginTest : KoinTest {

    val retrofitClient: Retrofit by inject{parametersOf(server.hostName)}
    val service = retrofitClient.create(DeviceService::class.java)
    val server = MockWebServer()

    @Before
    fun setup() {
        startKoin {
            modules(testModule)
        }.checkModules {
            create<DeviceService> { parametersOf(String())}
        }
        server.enqueue(MockResponse().setBody(MockResponseFileReader("C:\\Users\\jakov\\AndroidStudioProjects\\mirumon-users\\app\\src\\test\\java\\com\\redbox\\mirumon\\Token.json")))
//        server.enqueue(MockResponse().setBody(MockResponseFileReader("Devices.json").content))
        server.url("user/login")
    }

    @Test
    fun getToken() = runBlocking {
        val token = service.loginUser("admin1", "12345")
        assertThat(token.accessToken, equals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluMSIsInNjb3BlcyI6W10sImV4cCI6MTU5OTc1ODQ1OSwic3ViIjoiYWNjZXNzIn0.anEG24AXmD-KkgszFhf9IJJxAjq0yacL7YSaRaMSmgI"))
        assertThat(token.tokenType, equals("Bearer"))
    }

    @Test
    fun getDevice() = runBlocking {
        val token = "Bearer " + service.loginUser("admin1", "12345").accessToken
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