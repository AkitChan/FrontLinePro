package cn.akit.frontlinepro

import android.content.Context
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.akit.frontlinepro.common.base.BaseAct
import cn.akit.frontlinepro.common.base.BaseAdapter
import cn.akit.frontlinepro.common.base.BaseViewHolder
import kotlinx.android.synthetic.main.act_preload.*

/**
 * Created by chenYuXuan on 2019/4/22.
 * email : southxvii@163.com
 */
class PreloadAct : BaseAct() {

    override fun init() {

        val list = ArrayList<String>()
        for (i in 0 until 30) {
            list.add(" ")
        }

        val manager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        manager.stackFromEnd = true
        rv_list.layoutManager = manager
        val adapter = object : BaseAdapter<String>(this, list) {
            override fun createViewHolder(context: Context, viewType: Int, parent: ViewGroup, adapter: BaseAdapter<String>): BaseViewHolder<String> {
                return object : BaseViewHolder<String>(context, parent, this, R.layout.item_preload) {
                    override fun bindData(obj: String) {
                        getRootView()!!.findViewById<TextView>(R.id.tv_text).text = "Position:" + layoutPosition
                    }

                    override fun onItemClick(obj: String, position: Int) {

                        Toast.makeText(this@PreloadAct, "postition" + position, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        rv_list.adapter = adapter
        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//                if ((rv_list.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() < 20) {
//                    val temp  =ArrayList<String>()
//                    for (i in 0 until 20) {
//                        temp.add(" ")
//                    }
//                    adapter.addDatas(temp)
//                }
            }
        })

        object : CountDownTimer(100 * 1000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                val temp = ArrayList<String>()
                for (i in 0 until 1) {
                    temp.add(" ")
                }
                adapter.addDatas(temp)
            }
        }.start()
    }

    override fun provideViewRes(): Int {
        return R.layout.act_preload
    }

}