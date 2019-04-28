package cn.akit.frontlinepro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.akit.frontlinepro.common.base.BaseAct
import cn.akit.frontlinepro.common.base.BaseAdapter
import cn.akit.frontlinepro.common.base.BaseViewHolder
import com.socks.library.KLog
import kotlinx.android.synthetic.main.act_preload.*
import kotlinx.android.synthetic.main.item_main_act.view.*

/**
 * Created by chenYuXuan on 2019/4/24.
 * email : southxvii@163.com
 */
class MainAct : BaseAct() {
    override fun provideViewRes(): Int {
        return R.layout.act_main
    }

    val list = ArrayList<Type>()


    enum class Type(var value: String) {
        DEFAULT("empty"),
        NOTIFICATION("notification"),
        SYSTEM_JUMP("system_jump"),
        EXTERNAL_APP("open_external_app"),
    }

    override fun init() {
        initList()
        initRecycleView()
    }

    private fun initList() {
        list.add(Type.NOTIFICATION)
        list.add(Type.SYSTEM_JUMP)
        list.add(Type.EXTERNAL_APP)
    }

    private fun initRecycleView() {
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = manager
        val adapter = object : BaseAdapter<Type>(this, list) {
            override fun createViewHolder(context: Context, viewType: Int, parent: ViewGroup, adapter: BaseAdapter<Type>): BaseViewHolder<Type> {
                return object : BaseViewHolder<Type>(context, parent, this, R.layout.item_main_act) {
                    override fun bindData(obj: Type) {
                        getRootView().tv_text.text = obj.value
                    }

                    override fun onItemClick(obj: Type, position: Int) {
                        processItemClick(obj, position)
                    }
                }
            }
        }
        rv_list.adapter = adapter
    }

    private fun processItemClick(type: Type, position: Int) {
        when (type) {
            Type.NOTIFICATION -> {
                startActivity(Intent(this, NotificationAct::class.java))
            }
            Type.SYSTEM_JUMP -> {
                startActivity(Intent(this, SystemJumpAct::class.java))
            }
            Type.EXTERNAL_APP -> {
                externalApp()
            }
        }
    }

    private fun externalApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse("scheme://donghills/welcome2?id=123666"))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            //
            KLog.debug("无法找到app")
            Toast.makeText(this,"无法找到app",Toast.LENGTH_SHORT).show()
        }
    }
}
