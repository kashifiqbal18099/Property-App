package com.fyp.propertydealerapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.fyp.propertydealerapp.fragment.PendingTaskFragmentAdmin


abstract class GenericAdapter<T, D>(
    private val mContext: Context,
    private var mArrayList: List<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    abstract val layoutResId: Int

    abstract fun onBindData(model: T, position: Int, dataBinding: D)
    abstract fun onItemClick(model: T, position: Int, dataBinding: D)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutResId, parent, false
        )
        return ItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(mArrayList[position], position, (holder as GenericAdapter<*, *>.ItemViewHolder).mDataBinding as D)
        (holder.mDataBinding as ViewDataBinding).root.setOnClickListener {
            onItemClick(
                mArrayList[position],
                position,
                holder.mDataBinding as D
            )
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    fun addItems(arrayList: ArrayList<T>) {
        mArrayList = arrayList
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return mArrayList[position]
    }

    internal inner class ItemViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        var mDataBinding: D = binding as D

    }
}