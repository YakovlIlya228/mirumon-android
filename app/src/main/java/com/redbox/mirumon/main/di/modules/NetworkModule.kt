package com.redbox.mirumon.main.di.modules

import android.content.Context
import com.redbox.mirumon.BuildConfig.BASE_URL
import com.redbox.mirumon.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MINUTES

val networkModule = module {
    single { RxJava2CallAdapterFactory.create() }
    single { GsonConverterFactory.create() }
    single {

        val interceptorLogger = HttpLoggingInterceptor()
        interceptorLogger.level = (HttpLoggingInterceptor.Level.BODY)


//        val httpClient = OkHttpClient.Builder().addInterceptor {
//            val request = it.request().newBuilder()
//                .header(
//                    "Authorization",
//                    "Bearer " + androidApplication().getSharedPreferences(
//                        androidApplication().getString(R.string.pref_file_key), Context.MODE_PRIVATE
//                    ).getString("USER_TOKEN", "default")!!
//                )
//                .build()
//            return@addInterceptor it.proceed(request)
//        }

        val client = OkHttpClient.Builder()
            .connectTimeout(1, MINUTES)
            .writeTimeout(1, MINUTES)
            .readTimeout(1, MINUTES)
            .addInterceptor(interceptorLogger)
            .build()

        Retrofit.Builder()
            .addCallAdapterFactory(get() as RxJava2CallAdapterFactory)
            .addConverterFactory(get() as GsonConverterFactory)
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }
}