package cn.akit.frontlinepro.common.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by chenYuXuan on 2017/10/16.
 * email : southxvii@163.com
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>> {

    var listData: List<T>? = null
    var context: Context? = null


    constructor(context: Context, listData: ArrayList<T>?) {
        this.listData = listData
        this.context = context
    }

    constructor(context: Context, listData: List<T>?) {
        this.listData = listData
        this.context = context
    }

    open fun getDatas(): List<T>? {
        return this.listData;
    }

    open fun setDatas(listData: ArrayList<T>?) {
        this.listData = listData
        notifyDataSetChanged()
    }

    open fun setDatas(listData: List<T>?) {
        this.listData = listData
        notifyDataSetChanged()
    }

    open fun addDatas(list: ArrayList<T>?) {
        list?.let {
            if (listData == null) {
                listData = ArrayList()
            }
//        listData?.addAll(list)
            (listData as ArrayList).addAll(list)
            notifyDataSetChanged()

        }
    }

    open fun removeData(t: T?) {
        if (t != null) {
            if (listData != null && listData is ArrayList) {
                (listData as ArrayList).remove(t)
            }
        }
        notifyDataSetChanged()
    }


    open fun removeData(position: Int) {
        if (listData != null && listData is ArrayList) {
            (listData as ArrayList).removeAt(position)
            notifyItemRemoved(position)
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder?.init()
        if (listData?.get(position) != null) {
            holder?.bindData(listData?.get(position) as T)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return createViewHolder(context as Context, viewType, parent, this)
    }

    abstract fun createViewHolder(context: Context, viewType: Int, parent: ViewGroup, adapter: BaseAdapter<T>): BaseViewHolder<T>


    fun getItemBean(position: Int): T? {
        if (listData?.size as Int >= position) {
            return listData?.get(position) as T
        }
        return null
    }

    override fun getItemCount(): Int {
        when (listData == null) {
            true -> return 0
            false -> return listData?.size as Int
        }
    }

}