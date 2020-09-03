package com.redbox.mirumon.main.domain.pojo

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("id")
    val id: String,
    @SerializedName("online")
    val online: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("workgroup")
    val workgroup: String,
    @SerializedName("last_user")
    val lastUser: LastUser


)