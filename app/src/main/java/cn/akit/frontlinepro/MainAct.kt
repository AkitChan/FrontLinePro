package cn.akit.frontlinepro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import cn.akit.frontlinepro.act.*
import cn.akit.frontlinepro.common.base.BaseAct
import cn.akit.frontlinepro.common.base.BaseAdapter
import cn.akit.frontlinepro.common.base.BaseViewHolder
import cn.akit.frontlinepro.wcdb.AppDatabase
import cn.akit.frontlinepro.wcdb.Group
import cn.akit.frontlinepro.wcdb.User
import com.alibaba.android.arouter.launcher.ARouter
import com.socks.library.KLog
import com.tencent.wcdb.room.db.WCDBOpenHelperFactory
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.act_preload.rv_list
import kotlinx.android.synthetic.main.item_main_act.view.*
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory


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
        VIDEO_VIEW("VIDEO_VIEW"),
        DYNAMIC_VIEW_PAGER("DYNAMIC_VIEW_PAGER"),
        FLOAT_WINDOW("FLOAT_WINDOW"),
    }

    override fun init() {
        initList()
        initRecycleView()
//        pintTest()
//        test()
//        insetTest()
    }

    fun insetTest(){
        rv_list.postDelayed(Runnable {
            object :CountDownTimer(Long.MAX_VALUE,5000){
                override fun onFinish() {

                }

                override fun onTick(millisUntilFinished: Long) {
                    val data = ArrayList<Type>()
                    repeat(30) {
                        data.add(Type.NOTIFICATION)
                    }
                    list.addAll(0,data)
                    rv_list.adapter?.notifyItemRangeInserted(0,data.size)
                }
            }.start()
        },3000)
    }

    fun runTest() {
        val e = Executors.newCachedThreadPool(object : ThreadFactory {
            override fun newThread(r: Runnable?): Thread {
                return Thread(r)
            }
        })

        repeat(100) {
            e.execute {
                while (true) {

                }
            }
        }
    }

    private fun initList() {
        list.add(Type.NOTIFICATION)
        list.add(Type.SYSTEM_JUMP)
        list.add(Type.EXTERNAL_APP)
        list.add(Type.ENCRYPT)
        list.add(Type.URL_IN_TEXT_VIEW)
        list.add(Type.HANDLER)
        list.add(Type.BACKUP_MODE)
        list.add(Type.VIDEO_VIEW)
        list.add(Type.DYNAMIC_VIEW_PAGER)
        list.add(Type.FLOAT_WINDOW)
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
            Type.VIDEO_VIEW -> {
                startActivity(Intent(this, VideoViewAct::class.java))
            }
            Type.DYNAMIC_VIEW_PAGER -> {
                startActivity(Intent(this, DynamicViewPagerAct::class.java))
            }
            Type.FLOAT_WINDOW -> {
                startActivity(Intent(this, FloatWindowAct::class.java))
            }
        }
    }


    private fun a() {
        val list = ArrayList<String>()
        list.filter {
            it.contains("A")
        }
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
        val hostList = arrayOf("im.2233pro.com", "im2.2233pro.com", "im3.2233pro.com", "im4.2233pro.com", "im.1133pro.com", "google.com")

        Thread(Runnable {
            while (true) {
                for (s in hostList) {
                    Thread(Runnable {
                        val i = index
                        val time = System.currentTimeMillis()
                        try {
                            isAvailableByPing(i, s, time)
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


    @Throws(IOException::class, InterruptedException::class)
    fun isAvailableByPing(i: Int, ip: String?, time: Long): Boolean {
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
            sb.append("Index $i Host " + ip + " Duration " + (System.currentTimeMillis() - time) + " net " + exitValue + "\n")
            return exitValue == 0
        } finally {
            //在结束的时候应该对资源进行回收
            ipProcess?.destroy()
            runtime.gc()
        }
    }


    fun testClass() {

    }

    open class A {

    }

    class B : A() {

    }

    fun test() {
        val list = ArrayList<User>()
        repeat(100) {
            val user = User()
            user.firstName = "John_$it"
            user.lastName = "He"
            list.add(user)
        }

        val factory = WCDBOpenHelperFactory()
                .passphrase("passphrase".toByteArray())  // passphrase to the database, remove this line for plain-text
                .writeAheadLoggingEnabled(true)
                .asyncCheckpointEnabled(true)        // enable asynchronous checkpoint, remove if not needed

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "app-db")
                .allowMainThreadQueries()
                .openHelperFactory(factory)   // specify WCDBOpenHelperFactory when opening database
                .build()

        db.userDao().insert(list[6], list[7])

        KLog.debug("Test", "WCDB ${db.userDao().all}")


        val group1 = Group()
        group1.groupId = 1
        group1.name = "A"

        val group2 = Group()
        group2.groupId = 2
        group2.name = "B"

        db.groupDao().insert(group1, group2)
    }

}
