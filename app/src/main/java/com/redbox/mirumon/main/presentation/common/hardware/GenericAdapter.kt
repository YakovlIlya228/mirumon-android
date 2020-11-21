package com.redbox.mirumon.main.presentation.common.hardware

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var itemList = mutableListOf<T>()

    constructor()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            , viewType
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int = getLayoutId(position, itemList[position])

    fun update(items: List<T>) {
        itemList = items.toMutableList()
        notifyDataSetChanged()
    }

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    protected open fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(view, viewType)
    }

    internal interface Binder<T> {
        fun bind(data: T)
    }
}