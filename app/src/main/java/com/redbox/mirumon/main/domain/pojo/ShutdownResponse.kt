package com.redbox.mirumon.main.domain.pojo
import com.google.gson.annotations.SerializedName


data class ShutdownResponse(
    @SerializedName("detail")
    val detail: List<Detail>
)

data class Detail(
    @SerializedName("loc")
    val loc: List<String>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("type")
    val type: String
)