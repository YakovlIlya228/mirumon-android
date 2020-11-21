package com.redbox.mirumon.main.presentation.common.overview

import androidx.lifecycle.MutableLiveData
import com.redbox.mirumon.main.domain.pojo.Device
import com.redbox.mirumon.main.domain.pojo.OperatingSystem

sealed class OverViewState {
    object Loading : OverViewState()
    class Success(val data: Device) : OverViewState()
    class Error(val errorMsg: String) : OverViewState()
}