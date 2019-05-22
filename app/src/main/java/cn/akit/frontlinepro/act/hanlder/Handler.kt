package cn.akit.frontlinepro.act.hanlder

import com.socks.library.KLog

/**
 * 处理消息分发
 *
 *
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class Handler {

    private val looper: Looper

    constructor(looper: Looper) {
        this.looper = looper
    }

    fun disptchMessage(msg: Message) {
        handlerMessage(msg)
    }

    open fun handlerMessage(msg: Message) {
        KLog.debug("Test---> handler_thread ${Thread.currentThread().name} ${msg.what}")
    }

    fun sendMessage(msg: Message) {
        KLog.debug("Test---> process_thread ${Thread.currentThread().name} ${msg.what}")
        msg.targer = this
        looper.queue.enqueue(msg)
    }


}