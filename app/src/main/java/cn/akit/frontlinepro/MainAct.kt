package cn.akit.frontlinepro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.akit.frontlinepro.act.*
import cn.akit.frontlinepro.common.base.BaseAct
import cn.akit.frontlinepro.common.base.BaseAdapter
import cn.akit.frontlinepro.common.base.BaseViewHolder
import com.alibaba.android.arouter.launcher.ARouter
import com.socks.library.KLog
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.act_preload.rv_list
import kotlinx.android.synthetic.main.item_main_act.view.*
import java.io.IOException

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
        ENCRYPT("encrypt"),
        URL_IN_TEXT_VIEW("url_in_text_view"),
        HANDLER("handler"),
        BACKUP_MODE("BACKUP_MODE"),
    }

    override fun init() {
        initList()
        initRecycleView()
        pintTest()
    }

    private fun initList() {
        list.add(Type.NOTIFICATION)
        list.add(Type.SYSTEM_JUMP)
        list.add(Type.EXTERNAL_APP)
        list.add(Type.ENCRYPT)
        list.add(Type.URL_IN_TEXT_VIEW)
        list.add(Type.HANDLER)
        list.add(Type.BACKUP_MODE)
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
            Type.ENCRYPT -> {
                startActivity(Intent(this, EncryptAct::class.java))
            }
            Type.URL_IN_TEXT_VIEW -> {
                startActivity(Intent(this, UrlInTextViewAct::class.java))
            }
            Type.HANDLER -> {
                startActivity(Intent(this, HandlerAct::class.java))
            }
            Type.BACKUP_MODE -> {

            }
        }
    }


    private fun a(){
        
    }

    private fun startActivityByRouter(path: String) {
        ARouter.getInstance().build(path).navigation()
    }


    private fun externalApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("waterim://uat-im-qrcode.77877.site/welcome?token=hello"))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            //
            KLog.debug("无法找到app")
            Toast.makeText(this, "无法找到app", Toast.LENGTH_SHORT).show()
        }
    }


    val sb = StringBuffer()
    var index = 0

    fun pintTest() {
        val hostList = arrayOf("im.2233pro.com", "im2.2233pro.com", "im3.2233pro.com","im4.2233pro.com","im.1133pro.com","google.com")

        Thread(Runnable {
            while (true) {
                for (s in hostList) {
                    Thread(Runnable {
                        val i = index
                        val time = System.currentTimeMillis()
                        try {
                            isAvailableByPing(i,s,time)
                            tv_content.post {
                                tv_content.text = sb.toString()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            print("异常：" + e.message)
                            sb.append("Index $i Host " + s + " Duration " + (System.currentTimeMillis() - time) + " (异常)\n")
                            tv_content.post {
                                tv_content.text = sb.toString()
                            }
                        }
                    }).start()
                }


                index++

                Thread.sleep(500)
            }
        }).start()

    }


    @Throws(IOException::class,InterruptedException::class)
    fun isAvailableByPing(i:Int,ip: String?,time:Long): Boolean {
        var ip = ip
        if (ip == null || ip.length <= 0) {
            return false
        }

        val runtime = Runtime.getRuntime()
        var ipProcess: Process? = null
        try {
            //-c 后边跟随的是重复的次数，-w后边跟随的是超时的时间，单位是秒，不是毫秒，要不然也不会anr了
            ipProcess = runtime.exec("ping -c 3 -w 3 $ip")
            val exitValue = ipProcess!!.waitFor()
            sb.append("Index $i Host " + ip + " Duration " + (System.currentTimeMillis() - time)+" net " + exitValue + "\n")
            return exitValue == 0
        } finally {
            //在结束的时候应该对资源进行回收
            ipProcess?.destroy()
            runtime.gc()
        }
    }

}
