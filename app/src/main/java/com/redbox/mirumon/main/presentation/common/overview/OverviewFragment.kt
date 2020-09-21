package com.redbox.mirumon.main.presentation.common.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.redbox.mirumon.R
import com.redbox.mirumon.main.extensions.applyErrorState
import com.redbox.mirumon.main.extensions.applyTextLoadingState
import com.redbox.mirumon.main.extensions.applyTextSuccessState
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import kotlinx.android.synthetic.main.fragment_overview.common_arch_tv
import kotlinx.android.synthetic.main.fragment_overview.common_os_tv
import kotlinx.android.synthetic.main.fragment_overview.common_serial_tv
import kotlinx.android.synthetic.main.fragment_overview.common_version_tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class OverviewFragment : Fragment() {

//    private val vm: OverViewModel by viewModel()

    private val viewModel: DeviceListViewModel by sharedViewModel(named("AuthViewModel"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getDeviceDetail(viewModel.deviceId).observe(viewLifecycleOwner, Observer {
            common_os_tv.text = it.os[0].name
            common_arch_tv.text = it.os[0].arch
            common_serial_tv.text = it.os[0].serialNum
            common_version_tv.text = it.os[0].version
        })
//        vm.state.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is OverViewState.Loading -> {
//                    GlobalScope.launch(Dispatchers.IO) {
//                        vm.getOS()
//                    }
//                    applyTextLoadingState(
//                        common_os_tv,
//                        common_arch_tv,
//                        common_version_tv,
//                        common_serial_tv
//                    )
//                }
//                is OverViewState.Success -> {
//                    applyTextSuccessState(
//                        common_os_tv,
//                        common_arch_tv,
//                        common_version_tv,
//                        common_serial_tv
//                    )
//                    common_os_tv.text = it.os.caption
//                    common_arch_tv.text = it.os.architecture
//                    common_serial_tv.text = it.os.serial
//                    common_version_tv.text = it.os.version
//                }
//                is OverViewState.Error -> {
//                    applyErrorState()
//                }
//            }
//        })
    }
}
