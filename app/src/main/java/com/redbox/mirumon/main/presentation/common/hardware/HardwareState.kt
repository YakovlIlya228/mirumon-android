package com.redbox.mirumon.main.presentation.common.hardware

import com.redbox.mirumon.main.domain.pojo.Hardware

sealed class HardwareState {
    class SuccessCpu(val data: List<Hardware.CPU>) : HardwareState()
    class SuccessGpu(val data: List<Hardware.GPU>) : HardwareState()
    class SuccessMB(val data: Hardware.Motherboard) : HardwareState()
    class SuccessNet(val data: List<Hardware.Network>): HardwareState()
    class Error(val errorMsg: String) : HardwareState()
}