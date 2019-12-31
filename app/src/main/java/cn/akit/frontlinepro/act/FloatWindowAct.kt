package cn.akit.frontlinepro.act

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Intent
import android.view.View
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import com.lzf.easyfloat.interfaces.OnInvokeView

/**
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class FloatWindowAct : BaseAct() {

    override fun init() {

    }

    override fun provideViewRes(): Int {
        return R.layout.act_float_window
    }


    fun show(view: View) {
       showFloat()
    }

    fun hide(view: View) {
        FloatManager.hide()
    }

    fun showFloat(){
        FloatManager.initFloat(this, OnInvokeView { view ->
            view.setOnClickListener {
                FloatManager.hide()

                val pi = PendingIntent.getActivity(this,0,Intent(this, FloatWindowAct::class.java), FLAG_ONE_SHOT)
                pi.send()
            }
        })

        moveTaskToBack(true)
    }

    override fun onStop() {
        showFloat()
        super.onStop()
    }


    override fun onDestroy() {
        FloatManager.hide()
        super.onDestroy()
    }
}