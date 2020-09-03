package com.redbox.mirumon.main.presentation.server.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Server::class),version = 1)
abstract class ServerDatabase: RoomDatabase() {
    abstract fun serverDao(): DAO
    companion object{
        val dbName = "serverDatabase"
    }

}