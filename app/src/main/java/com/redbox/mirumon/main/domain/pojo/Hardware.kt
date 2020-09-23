package com.redbox.mirumon.main.domain.pojo

import com.google.gson.annotations.SerializedName

data class Hardware (
    @SerializedName("motherboard")
    val motherboard: Motherboard,
    @SerializedName("cpu")
    val cpu: List<CPU>
){
    data class Motherboard(
        @SerializedName("name")
        val name: String,
        @SerializedName("product")
        val product: String,
        @SerializedName("serial_number")
        val serial_number: String
    )
     data class CPU(
         @SerializedName("name")
         val name: String,
         @SerializedName("current_clock_speed")
         val clockSpeed: String,
         @SerializedName("number_of_cores")
         val cores: Int,
         @SerializedName("number_of_enabled_cores")
         val enabledCores: Int,
         @SerializedName("number_of_logical_processors")
         val logicalProcessors: Int
     )
}