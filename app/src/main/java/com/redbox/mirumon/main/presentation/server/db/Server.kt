package com.redbox.mirumon.main.presentation.server.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Server(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val serverLink: String
)