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
    val lastUser: LastUser,
    @SerializedName("os")
    val os: List<OS>
){
    data class LastUser(
        @SerializedName("name")
        val name: String,
        @SerializedName("fullname")
        val fullName: String,
        @SerializedName("domain")
        val domain: String
    )

    data class OS(
        @SerializedName("name")
        val name: String,
        @SerializedName("version")
        val version: String,
        @SerializedName("os_architecture")
        val arch: String,
        @SerializedName("serial_number")
        val serialNum: String,
        @SerializedName("number_of_users")
        val usersNum: Int
    )
}