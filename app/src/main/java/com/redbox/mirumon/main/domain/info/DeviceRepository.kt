package com.redbox.mirumon.main.domain.info

import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.domain.pojo.Command
import com.redbox.mirumon.main.domain.pojo.DeviceInfo
import com.redbox.mirumon.main.domain.pojo.Software
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DeviceRepository(private val service: DeviceService) {

    suspend fun getSoftware() = service.getSoftware(CommonRepository.getAddress())

    suspend fun getOS() = service.getOS(CommonRepository.getAddress())

    suspend fun getDeviceInfo() = service.getDetails(CommonRepository.getAddress())

    suspend fun shutdownPC() = service.shutdownPC(CommonRepository.getAddress())

    suspend fun executeCommand(command: Command) = service.executeCommand(CommonRepository.getAddress(), command)
}