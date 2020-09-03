package com.redbox.mirumon.main.di.modules

import androidx.room.Room
import com.redbox.mirumon.main.presentation.server.ServerViewModel
import com.redbox.mirumon.main.presentation.server.db.ServerDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val serverModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ServerDatabase::class.java, ServerDatabase.dbName
        ).build()
    }
    single { get<ServerDatabase>().serverDao() }
    viewModel { ServerViewModel(get(named("Auth"))) }
}