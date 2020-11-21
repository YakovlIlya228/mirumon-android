package com.redbox.mirumon.main.domain.info

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.redbox.mirumon.main.domain.pojo.*
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface DeviceService {

    @GET("/computers/{mac-address}/details")
    suspend fun getDetails(@Path("mac-address") id: String): DeviceInfo

    @GET("/computers/{mac-address}/installed-programs")
    suspend fun getSoftware(@Path("mac-address") id: String): ArrayList<Software>

    @GET("/computers/{mac-address}/details")
    suspend fun getOS(@Path("mac-address") id: String): DeviceInfo

    @POST("/computers/{mac-address}/shutdown")
    suspend fun shutdownPC(@Path("mac-address") id: String): String

    @POST("/computers/{mac-address}/execute")
    suspend fun executeCommand(@Path("mac-address") id: String, @Body command: Command): String

    @GET("devices/{id}/software")
    suspend fun getDeviceSoftware(@Path("id") id: String): List<Software>

    @GET("devices/{id}/detail")
    suspend fun getDeviceDetail(@Path("id") id: String): Device

    @GET("devices/{id}/hardware")
    suspend fun getDeviceHardware(@Path("id") id: String): Hardware

    @FormUrlEncoded
    @POST("/users/token")
    suspend fun loginUser(@Field("username") username: String, @Field("password") password: String): Token

    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("/devices")
    suspend fun getDevices(): List<Device>

    @POST("/devices/{id}/shutdown")
    suspend fun shutdown(@Path("id") id: String): Response<Unit>





}