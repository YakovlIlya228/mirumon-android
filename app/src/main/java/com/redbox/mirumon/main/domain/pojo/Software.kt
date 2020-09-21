package com.redbox.mirumon.main.domain.pojo

import com.google.gson.annotations.SerializedName

data class Software(
    @SerializedName("name")
    val name: String,
    @SerializedName("vendor")
    val vendor: String,
    @SerializedName("version")
    val version: String
)