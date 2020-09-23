package com.redbox.mirumon.main.presentation.common.hardware

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Hardware
import kotlinx.android.synthetic.main.cpu_list_item.view.*

class CPUListAdapter: RecyclerView.Adapter<CPUListAdapter.CPUListViewholder>() {

    var cpuList = ArrayList<Hardware.CPU>()

    class CPUListViewholder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cpuName = itemView.cpu_name_tv
        val cpuCoreSpeed = itemView.cpu_speed_tv
        val cores = itemView.cores_tv
        val enabledCores = itemView.enabled_cores_tv
        val logicalProc = itemView.logical_proc_tv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CPUListViewholder {
        return CPUListViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cpu_list_item,
                parent,
                false
            )
        )
    }


    fun setList(list: ArrayList<Hardware.CPU>){
        cpuList = list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return cpuList.size
    }

    override fun onBindViewHolder(holder: CPUListViewholder, position: Int) {
        holder.cpuName.text = cpuList[position].name
        holder.cpuCoreSpeed.text = cpuList[position].clockSpeed
        holder.cores.text = cpuList[position].cores.toString()
        holder.enabledCores.text = cpuList[position].enabledCores.toString()
        holder.logicalProc.text = cpuList[position].logicalProcessors.toString()
    }
}