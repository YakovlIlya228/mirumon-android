package com.redbox.mirumon.main.domain.info

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.redbox.mirumon.main.domain.pojo.*
import io.reactivex.Single
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

    @POST("/v1/oauth/token")
    suspend fun loginUser(@Body loginUser: LoginUser): Token

    @GET("/v1/devices")
    suspend fun getDevices(@Header("Authorization: Bearer") token: String): LiveData<List<Device>>

}