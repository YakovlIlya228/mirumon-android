package com.redbox.mirumon.main.di

import com.redbox.mirumon.main.di.modules.infoModule
import com.redbox.mirumon.main.di.modules.networkModule
import com.redbox.mirumon.main.di.modules.serverModule
import com.redbox.mirumon.main.di.modules.testModule

val modules = mutableListOf(networkModule, infoModule)