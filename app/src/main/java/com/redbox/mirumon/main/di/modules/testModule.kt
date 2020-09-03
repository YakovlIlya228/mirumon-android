package com.redbox.mirumon.main.di.modules

import com.redbox.mirumon.BuildConfig.BASE_URL
import com.redbox.mirumon.main.domain.info.DeviceService
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val testModule = module {
    single { GsonConverterFactory.create() }
    single { androidContext() }
    single(named("noAuth")) {
        Retrofit.Builder()
            .addConverterFactory(get() as GsonConverterFactory)
            .baseUrl(BASE_URL)
            .build()
    }
    single(named("noAuthService")) { get<Retrofit>(named("noAuth")).create(DeviceService::class.java)}
}