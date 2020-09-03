package com.redbox.mirumon.main.presentation.server

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.redbox.mirumon.main.presentation.server.db.Server

class ServerAutoCompleteAdapter: BaseAdapter() {

    lateinit var items: ArrayList<Server>

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position].serverLink
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}