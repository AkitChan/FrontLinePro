package cn.akit.frontlinepro.act.hanlder

/**
 * 消息轮询
 *
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class Looper {

    companion object {
        private val thread = ThreadLocal<Looper>()

        @JvmStatic
        fun prepare() {
            if (myLooper() != null) {
                throw RuntimeException("Looper is created.")
            }

            thread.set(Looper())
        }

        @JvmStatic
        fun myLooper(): Looper? {
            return thread.get()
        }
    }

     val  queue = MessageQueue()

    private constructor() {
    }


    fun loop() {
        while (true) {
            //为空就线程等待
            val msg = queue.next()

            if (msg != null) {
                val handler = msg.targer
                //主线程分发
                handler?.disptchMessage(msg)
            }
        }
    }

}