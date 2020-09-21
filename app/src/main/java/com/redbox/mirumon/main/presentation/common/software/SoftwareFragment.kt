package com.redbox.mirumon.main.presentation.common.software

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Software
import com.redbox.mirumon.main.extensions.applyErrorState
import com.redbox.mirumon.main.extensions.applyLoadingState
import com.redbox.mirumon.main.extensions.applySuccessState
import com.redbox.mirumon.main.presentation.common.CommonInfoActivity
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import kotlinx.android.synthetic.main.fragment_software.common_software_btn
import kotlinx.android.synthetic.main.fragment_software.software_list_rv
import kotlinx.android.synthetic.main.fragment_software.software_pv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SoftwareFragment : Fragment(){

//    private val vm: SoftwareViewModel by viewModel()
    private val viewModel: DeviceListViewModel by sharedViewModel(named("AuthViewModel"))
    private val adapter = SoftwareListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_software, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        software_list_rv.layoutManager = LinearLayoutManager(this.context)
        software_list_rv.adapter = adapter
        common_software_btn.setActionListener {
            viewModel.getDeviceSoftware(viewModel.deviceId).observe(viewLifecycleOwner, Observer {
                adapter.setList(it as ArrayList<Software>)
                software_list_rv.isVisible = common_software_btn.stateOpened
            })
        }
//        software_list_rv.adapter = adapter
//        vm.state.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is SoftwareState.Initial -> {
//                    GlobalScope.launch(Dispatchers.IO) {
//                        vm.getSoftware()}
//                }
//                is SoftwareState.Loading -> {
//                    common_software_btn.setActionListener {
//                        software_pv.isVisible = !software_pv.isVisible
//                    }
//                    applyLoadingState(software_list_rv)
//                }
//                is SoftwareState.Success -> {
//                    applySuccessState(software_pv)
//                    adapter = SoftwareListAdapter((it.softList))
//                    software_list_rv.adapter = adapter
//                    software_list_rv.isVisible = common_software_btn.stateOpened
//                    common_software_btn.setActionListener {
//                        software_list_rv.isVisible = !software_list_rv.isVisible
//                    }
//                }
//                is SoftwareState.Error -> {
//                    applyErrorState()
//                }
//            }
//        })
    }
}
