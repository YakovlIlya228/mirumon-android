package com.redbox.mirumon.main.domain.info

import com.redbox.mirumon.main.domain.pojo.Command
import com.redbox.mirumon.main.domain.pojo.DeviceInfo
import com.redbox.mirumon.main.domain.pojo.Software
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}