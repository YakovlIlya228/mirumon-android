package com.redbox.mirumon.main.domain.pojo

import com.google.gson.annotations.SerializedName

data class Hardware (
    @SerializedName("name")
    val name: String,
    @SerializedName("product")
    val product: String,
    @SerializedName("serial_number")
    val serial_number: String
)