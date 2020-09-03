package com.redbox.mirumon.main.presentation.server

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbox.mirumon.main.domain.info.DeviceService
import com.redbox.mirumon.main.domain.pojo.Device
import com.redbox.mirumon.main.domain.pojo.LoginUser
import com.redbox.mirumon.main.domain.pojo.Token
import com.redbox.mirumon.main.presentation.server.db.DAO
import com.redbox.mirumon.main.presentation.server.db.Server
import kotlinx.coroutines.launch

class ServerViewModel(val dao: DAO,val  service: DeviceService): ViewModel() {

    suspend fun getServers(): List<Server> =  dao.getAll()

    suspend fun getServer(id: Int): Server = dao.getServer(id)

    suspend fun loginUser(user: LoginUser): Token = service.loginUser(user)

//    fun getDevices(token: Token): LiveData<List<Device>> = service.getDevices(token.tokenType + token.accessToken)

    fun insertServer(server: Server) = viewModelScope.launch{dao.insert(server)}
}