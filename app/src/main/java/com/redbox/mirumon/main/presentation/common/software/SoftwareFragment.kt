package com.redbox.mirumon.main.presentation.common.software

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.domain.pojo.Software
import com.redbox.mirumon.main.presentation.common.hardware.GenericAdapter
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import com.redbox.mirumon.main.presentation.util.RevealButton.Companion.animateView
import kotlinx.android.synthetic.main.fragment_software.common_software_btn
import kotlinx.android.synthetic.main.fragment_software.software_list_rv
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class SoftwareFragment : Fragment(){

    private val viewModel: DeviceListViewModel by sharedViewModel(named("AuthViewModel"))
    private lateinit var softwareAdapter: GenericAdapter<Software>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_software, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        software_list_rv.layoutManager = LinearLayoutManager(requireContext())
        softwareAdapter = object : GenericAdapter<Software>(){
            override fun getLayoutId(position: Int, obj: Software): Int = R.layout.software_list_item
        }
        software_list_rv.adapter = softwareAdapter
        with(viewModel){
            software.observe(viewLifecycleOwner){
                when(it){
                    is SoftwareState.Success -> softwareAdapter.update(it.data)
                    is SoftwareState.Error -> Toast.makeText(context,it.errorMsg, Toast.LENGTH_SHORT).show()
                    else -> TODO()
                }

            }
        }
        viewModel.getDeviceSoftware(CommonRepository.getAddress())
        common_software_btn.setActionListener {
            animateView(requireContext(),software_list_rv,common_software_btn)
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
