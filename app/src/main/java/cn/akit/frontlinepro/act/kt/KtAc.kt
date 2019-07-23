package cn.akit.frontlinepro.act.kt

import android.view.View
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import com.socks.library.KLog

const val A = "AAA"

/**
 * Created by chenYuXuan on 2019/7/8.
 * email : southxvii@163.com
 * [@param value][@link aaa]
 */
class KtAc : BaseAct() {

    lateinit var name : String

    protected var value :String  = ""
    set(value) {
        KLog.debug()
        field = value
    }
     get() {
        KLog.debug()
        return field
    }

    override fun provideViewRes(): Int {
        return R.layout.act_kt
    }

    override fun init() {
    }


    fun click(view: View) {
        A()
    }

    internal fun a(){

    }
}