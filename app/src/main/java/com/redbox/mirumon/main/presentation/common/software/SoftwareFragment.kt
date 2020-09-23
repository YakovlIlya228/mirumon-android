package com.redbox.mirumon.main.presentation.common.software

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Software
import com.redbox.mirumon.main.presentation.common.CommonRepository
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import com.redbox.mirumon.main.presentation.util.RevealButton.Companion.animateView
import kotlinx.android.synthetic.main.fragment_software.common_software_btn
import kotlinx.android.synthetic.main.fragment_software.software_list_rv
import org.koin.android.viewmodel.ext.android.sharedViewModel
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
            viewModel.getDeviceSoftware(CommonRepository.getAddress()).observe(viewLifecycleOwner, Observer {
                adapter.setList(it as ArrayList<Software>)
                context?.let { it1 -> animateView(it1,software_list_rv,common_software_btn) }
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
