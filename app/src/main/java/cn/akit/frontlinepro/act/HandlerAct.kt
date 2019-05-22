package cn.akit.frontlinepro.act

import android.view.View
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.act.hanlder.Handler
import cn.akit.frontlinepro.act.hanlder.Looper
import cn.akit.frontlinepro.act.hanlder.Message
import cn.akit.frontlinepro.common.base.BaseAct
import com.socks.library.KLog
import java.util.*

/**
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class HandlerAct : BaseAct() {

    var handler: Handler? = null
    override fun init() {
        KLog.debug("Test--->")
        Thread(Runnable {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            val looper = Looper.myLooper()
            handler = Handler(looper!!)
            looper.loop()
            KLog.debug("Test--->")
        }).start()
    }

    override fun provideViewRes(): Int {
        return R.layout.act_handler
    }

    fun doLoop(view: View) {
        for (i in 0 until 5) {
            Thread(Runnable {
                while (true) {
                    KLog.debug("Test---> $handler")
                    handler?.sendMessage(Message(UUID.randomUUID().toString()))
                    Thread.sleep(50)
                }
            }).start()
        }
    }


}