package cn.akit.frontlinepro.act.hanlder

/**
 * 消息载体
 *
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class Message(val what:String){

    var targer:Handler? = null

}