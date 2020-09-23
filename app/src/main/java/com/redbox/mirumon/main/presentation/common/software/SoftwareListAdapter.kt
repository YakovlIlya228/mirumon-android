package com.redbox.mirumon.main.presentation.common.software

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Software
import kotlinx.android.synthetic.main.software_list_item.view.software_name_tv
import kotlinx.android.synthetic.main.software_list_item.view.software_vendor_tv
import kotlinx.android.synthetic.main.software_list_item.view.software_version_tv

class SoftwareListAdapter :
    RecyclerView.Adapter<SoftwareListAdapter.SoftwareViewHolder>() {

    var softwareList = ArrayList<Software>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoftwareViewHolder {
        return SoftwareViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.software_list_item,
                parent,
                false
            )
        )
    }
    fun setList(list: ArrayList<Software>){
        this.softwareList = list
        notifyDataSetChanged()
    }
    override fun getItemCount() = softwareList.size

    override fun onBindViewHolder(holder: SoftwareViewHolder, position: Int) {
        holder.softNameTv.text = softwareList[position].name
        holder.softVendorTv.text = softwareList[position].vendor
        holder.softVersionTv.text = softwareList[position].version
    }

    class SoftwareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val softNameTv: TextView = itemView.software_name_tv
        val softVendorTv: TextView = itemView.software_vendor_tv
        val softVersionTv: TextView = itemView.software_version_tv
    }
}