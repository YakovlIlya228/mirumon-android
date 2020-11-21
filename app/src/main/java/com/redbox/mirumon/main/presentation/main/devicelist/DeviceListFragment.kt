package com.redbox.mirumon.main.presentation.main.devicelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Device
import kotlinx.android.synthetic.main.fragment_device_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DeviceListFragment : Fragment() {

    val listViewModel: DeviceListViewModel by viewModel(named("AuthViewModel"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        listViewModel = ViewModelProviders.of(this).get(DeviceListViewModel::class.java)
//        lifecycle.addObserver(listViewModel)
        return inflater.inflate(R.layout.fragment_device_list, container, false)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DeviceListAdapter(listViewModel)
        device_list_rv.adapter = adapter
        listViewModel.getDevices().observe(viewLifecycleOwner, Observer {
                adapter.setList(it as ArrayList<Device>)
            })

//        GlobalScope.launch(Dispatchers.IO) {
//            device_list_rv.adapter = DeviceListAdapter(listViewModel::shutDown, listViewModel.getDevices())
//            listViewModel.getDevices()
//        }


        list_refresh.setColorSchemeResources(
            R.color.colorPrimaryDark
        )

        list_refresh.setOnRefreshListener {
            GlobalScope.async(Dispatchers.IO) {
                val result = listViewModel.refreshDevices() as ArrayList<Device>
                adapter.setList(result)
            }

            list_refresh.isRefreshing = false
        }

//        listViewModel.observeDevices(this) {
//            adapter = DeviceListAdapter(listViewModel::shutDown, it)
//            device_list_rv.adapter = adapter
//            adapter.notifyDataSetChanged()
//        }

        device_list_rv.layoutManager = LinearLayoutManager(this.context)
    }

}