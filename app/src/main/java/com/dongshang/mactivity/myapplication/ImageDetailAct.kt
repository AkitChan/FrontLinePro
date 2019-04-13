package com.dongshang.mactivity.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView


/**
 * Created by chenYuXuan on 2019/4/12.
 * email : southxvii@163.com
 */
class ImageDetailAct : AppCompatActivity() {

    var iv_temp: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_image_detail)

        iv_temp = findViewById(R.id.iv_temp)

        mPosArr = intent.getIntegerArrayListExtra("location")
//        computerAnimationParams()
        doReadyJob()
        iv_temp!!.setOnClickListener {
            if (!b) {
                showEnterAnim()
            } else {
                showOutAnim()
            }
        }
    }

    var mPosArr = ArrayList<Int>()
    var mOriginWidth = 0
    var mRadio = 0f
    var mTransition = arrayOf(0f, 0f)

    var b = false

    private fun showEnterAnim() {
        b = true
        val enterAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(600)
        enterAnimator.interpolator = AccelerateDecelerateInterpolator()
        enterAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            iv_temp?.setAlpha(1f)
            iv_temp?.setScaleX(1 + (mRadio - 1) * value)
            iv_temp?.setScaleY(1 + (mRadio - 1) * value)
            iv_temp?.setTranslationX(mTransition[0] * value)
            iv_temp?.setTranslationY(-mTransition[1] * (1f - value))
            iv_temp?.invalidate()
        }
        enterAnimator.start()
    }

    /**
     * 模拟退场动画
     */
    private fun showOutAnim() {
        val exitAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(600)
        iv_temp?.setAlpha(1f)
        exitAnimator.interpolator = AccelerateDecelerateInterpolator()
        exitAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            iv_temp?.setScaleX((mRadio - 1) * value + 1)
            iv_temp?.setScaleY((mRadio - 1) * value + 1)
            iv_temp?.setTranslationX(mTransition[0] * value)
            iv_temp?.setTranslationY(-mTransition[1] * (1f - value))
        }
        exitAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
//                quitActivityWithoutAnimation()
                finish()
                overridePendingTransition(0, 0)
            }
        })
        exitAnimator.start()

    }


    private fun getStatusBarHeight(): Int {
        return 0
    }

    /**
     * 计算动画位移和缩放
     */
    private fun computerAnimationParams() {
        if (mPosArr.size !== 4) {
            return
        }


        var metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)

        val screenHigh = metrics.heightPixels
        val screenWidth = metrics.widthPixels
        // 计算可用于显示图片的屏幕高度
        val availableScreenHeight = screenHigh - getStatusBarHeight()

        val imageHeight = mPosArr.get(3) - mPosArr.get(2)
        mOriginWidth = mPosArr.get(1) - mPosArr.get(0)

        val radioWidth = screenWidth * 1.0f / mOriginWidth
        val radioHeight = availableScreenHeight * 1.0f / imageHeight
        // 获取放大系数
        mRadio = Math.min(radioHeight, radioWidth)

        // 计算位移距离，屏幕中心与图片中心的距离，
        mTransition[0] = (screenWidth / 2 - (mPosArr.get(0) + (mPosArr.get(1) - mPosArr.get(0)) / 2)).toFloat()
        mTransition[1] = (availableScreenHeight / 2 - (mPosArr.get(2) - getStatusBarHeight() + (mPosArr.get(3) - mPosArr.get(2)) / 2)).toFloat()
    }

    /**
     * 动画前的准备工作
     */
    private fun doReadyJob() {
        // 需要对图片的宽度进行调整
        (iv_temp?.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.width = mPosArr[2] - mPosArr[0]
            it.height = mPosArr[3] - mPosArr[1]
            it.leftMargin = mPosArr[1]
            it.topMargin = mPosArr[2]
            iv_temp?.setLayoutParams(it)
            return@let
        }
    }
}