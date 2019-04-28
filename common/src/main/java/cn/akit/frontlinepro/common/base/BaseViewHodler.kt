package cn.akit.frontlinepro.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chenYuXuan on 2017/10/16.
 * email : southxvii@163.com
 */
abstract class BaseViewHolder<T> : RecyclerView.ViewHolder {

    var adapter: BaseAdapter<T>? = null
    var bean: T? = null
    var context: Context? = null


    constructor(context: Context, parent: ViewGroup, adapter: BaseAdapter<T>, layoutRes: Int)
            : super(LayoutInflater.from(context).inflate(layoutRes, parent, false)) {
        this.adapter = adapter
        this.context = context
    }

    init {

    }

    abstract fun bindData(obj: T)

    fun init(): BaseViewHolder<T> {
        this.bean = adapter?.listData?.get(layoutPosition)
        this.itemView?.setOnClickListener { onItemClick(bean as T, layoutPosition) }
        this.itemView?.setOnLongClickListener { onItemLongClick(bean as T, layoutPosition) }
        return this
    }

    open fun onItemClick(obj: T, position: Int) {}
    open fun onItemLongClick(obj: T, position: Int): Boolean {
        return false
    }

    fun getRootView(): View {
        return this.itemView
    }

}