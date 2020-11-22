package com.redbox.mirumon.main.domain.pojo

import com.google.gson.annotations.SerializedName

data class Hardware(
    @SerializedName("motherboard")
    val motherboard: Motherboard,
    @SerializedName("cpu")
    val cpu: List<CPU>,
    @SerializedName("gpu")
    val gpu: List<GPU>,
    @SerializedName("network")
    val network: List<Network>
) {
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

    data class GPU(
        @SerializedName("caption")
        val caption: String,
        @SerializedName("current_vertical_resolution")
        val currentVerticalResolution: String,
        @SerializedName("driver_date")
        val driverDate: String,
        @SerializedName("driver_version")
        val driverVersion: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("video_mode_description")
        val videoModeDescription: String
    )

    data class Network(
        @SerializedName("description")
        val description: String,
        @SerializedName("ip_addresses")
        val ipAddresses: List<String>,
        @SerializedName("mac_address")
        val macAddress: String
    )
}
