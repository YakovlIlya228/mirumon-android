package com.redbox.mirumon.main.presentation.server.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface DAO {
    @Insert
    suspend fun insert(server: Server)
    @Query("SELECT * FROM SERVER ORDER BY id ASC")
    suspend fun getAll(): List<Server>
    @Query("SELECT * FROM SERVER WHERE id = :id")
    suspend fun getServer(id: Int): Server
}