package com.redbox.mirumon.main.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.redbox.mirumon.BuildConfig.*
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.info.DeviceService
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import com.redbox.mirumon.main.presentation.server.ServerViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

val networkModule = module {
    single { RxJava2CallAdapterFactory.create() }
    single { GsonConverterFactory.create() }
    viewModel(named("noAuthViewModel")) { ServerViewModel(get(named("noAuthService"))) }
    viewModel(named("AuthViewModel")) { DeviceListViewModel(get(named("AuthService"))) }
    //Retrofit clients with or w/o authentication
    single(named("noAuth")) {
        Retrofit.Builder()
            .addConverterFactory(get() as GsonConverterFactory)
            .baseUrl(BASE_URL)
            .build()
    }
    single(named("Auth")) {
        Retrofit.Builder()
            .addCallAdapterFactory(get() as RxJava2CallAdapterFactory)
            .addConverterFactory(get() as GsonConverterFactory)
            .client(get())
            .baseUrl(get<SharedPreferences>().getString(USER_SERVER, null))
            .build()
    }
    //Retrofit services with or w/o authentication
    single(named("noAuthService")) { get<Retrofit>(named("noAuth")).create(DeviceService::class.java) }
    single(named("AuthService")) { get<Retrofit>(named("Auth")).create(DeviceService::class.java) }
    single {
        androidApplication().getSharedPreferences(
            androidApplication().getString(R.string.pref_file_key),
            Context.MODE_PRIVATE
        )
    }
    //OkHttp client with token
    single {
        val token: String = "Bearer " + get<SharedPreferences>().getString(USER_TOKEN, null)
        Log.d("Token", token)
        val httpClient = OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder()
                .header(
                    "Authorization",
                    token
                )
                .build()
            return@addInterceptor it.proceed(request)
        }
        val interceptorLogger = HttpLoggingInterceptor()
        interceptorLogger.level = (HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptorLogger)
        httpClient.readTimeout(60, SECONDS)
        httpClient.writeTimeout(60, SECONDS)
        httpClient.build()
    }
}