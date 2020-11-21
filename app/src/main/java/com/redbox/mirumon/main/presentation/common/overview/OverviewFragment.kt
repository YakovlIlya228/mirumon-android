package com.redbox.mirumon.main.presentation.common.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import kotlinx.android.synthetic.main.fragment_overview.common_arch_tv
import kotlinx.android.synthetic.main.fragment_overview.common_os_tv
import kotlinx.android.synthetic.main.fragment_overview.common_serial_tv
import kotlinx.android.synthetic.main.fragment_overview.common_version_tv
import org.koin.android.viewmodel.ext.android.sharedViewModel
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
        with(viewModel){
            device.observe(viewLifecycleOwner){
                when(it){
                    is OverViewState.Success ->{
                        common_os_tv.text = it.data.os[0].name
                        common_arch_tv.text = it.data.os[0].arch
                        common_serial_tv.text = it.data.os[0].serialNum
                        common_version_tv.text = it.data.os[0].version
                    }
                    is OverViewState.Error ->
                        Toast.makeText(context,it.errorMsg, Toast.LENGTH_SHORT).show()

                    else -> TODO()
                }

            }
        }
        viewModel.getDeviceDetail(CommonRepository.getAddress())
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
