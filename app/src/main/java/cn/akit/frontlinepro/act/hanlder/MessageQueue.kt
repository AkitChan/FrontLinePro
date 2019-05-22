package cn.akit.frontlinepro.act.hanlder

import com.socks.library.KLog
import java.util.*
import java.util.concurrent.locks.ReentrantLock

/**
 * 消息队列
 *
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class MessageQueue {

    val queue = LinkedList<Message>()
    private val lock = ReentrantLock()
    private var notEmpty = lock.newCondition()
    private var notFull = lock.newCondition()

    fun enqueue(msg: Message) {
        lock.lock()
        KLog.debug("Test---> lock 尝试入队 ")
        try {
            while (queue.size > 50) {
                KLog.debug("Test---> lock 入队已满 ")
                notFull.await()//会释放锁
            }

            KLog.debug("Test---> lock 入队成功 ")
            queue.add(msg)

            //唤醒空缓冲
            notEmpty.signalAll()
        } catch (e: Exception) {

        } finally {
            lock.unlock()
        }
    }

    fun next(): Message? {
        lock.lock()
        KLog.debug("Test---> lock 尝试出队 ")
        var msg: Message? = null
        try {
            while (queue.size <= 0) {
                KLog.debug("Test---> lock 队列为空 ")
                notEmpty.await()
            }

            KLog.debug("Test---> lock 出队成功 ")
            msg = queue.poll()

            //唤醒缓冲完全
            notFull.signalAll()
        } catch (e: Exception) {

        } finally {
            lock.unlock()
        }
        return msg
    }

}