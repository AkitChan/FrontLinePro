package cn.akit.frontlinepro.act

import android.app.Activity
import android.util.Log
import android.view.Gravity
import cn.akit.frontlinepro.App
import cn.akit.frontlinepro.R
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.utils.floatNotification
import com.yhao.floatwindow.PermissionListener
import com.yhao.floatwindow.ViewStateListener

/**
 * 2019/11/18.
 * What does it do?
 *
 *
 */
object FloatManager {


    fun initFloat(activity:Activity,onInvokeView: OnInvokeView) {
//        FloatWindow
//                .with(App.context)
//                .setView(view)
//                .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
//                .setHeight(Screen.width, 0.6f)
//                .setX(Screen.width, 0.8f)
//                .setY(Screen.height, 0.3f)
//                .setMoveType(MoveType.active)
//                .setMoveStyle(500, AccelerateInterpolator())
//                //                .setMoveStyle(500, new BounceInterpolator())
//                .setViewStateListener(mViewStateListener)
//                .setPermissionListener(mPermissionListener)
//                .setDesktopShow(true)
//                .build()
        EasyFloat.with(activity)
                .setLayout(R.layout.layout_float, onInvokeView)
                .setShowPattern(ShowPattern.ALL_TIME)
                .setGravity(Gravity.END, -10, 260)
                .startForeground(true, floatNotification(activity))
                .show()
    }

    fun hide(){
//        FloatWindow.destroy()
//        EasyFloat.hide()
        EasyFloat.dismissAppFloat(App.context)
    }


    private val TAG = "FloatWindow"

    private val mPermissionListener = object : PermissionListener {
        override fun onSuccess() {
            Log.d(TAG, "onSuccess")
        }

        override fun onFail() {
            Log.d(TAG, "onFail")
        }
    }

    private val mViewStateListener = object : ViewStateListener {
        override fun onPositionUpdate(x: Int, y: Int) {
            Log.d(TAG, "onPositionUpdate: x=$x y=$y")
        }

        override fun onShow() {
            Log.d(TAG, "onShow")
        }

        override fun onHide() {
            Log.d(TAG, "onHide")
        }

        override fun onDismiss() {
            Log.d(TAG, "onDismiss")
        }

        override fun onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart")
        }

        override fun onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd")
        }

        override fun onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop")
        }
    }


}