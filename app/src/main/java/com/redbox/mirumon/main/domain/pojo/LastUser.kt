package com.redbox.mirumon.main.domain.pojo

import com.google.gson.annotations.SerializedName

data class LastUser(
    @SerializedName("name")
    val name: String,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("domain")
    val domain: String
)