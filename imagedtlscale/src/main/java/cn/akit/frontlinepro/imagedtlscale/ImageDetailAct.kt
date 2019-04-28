package cn.akit.frontlinepro.imagedtlscale

import android.animation.ValueAnimator
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.socks.library.KLog
import java.util.*


/**
 * Created by chenYuXuan on 2019/4/12.
 * email : southxvii@163.com
 */
class ImageDetailAct : AppCompatActivity() {

    var iv_temp: ImageView? = null
    var iv_temp2: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_image_detail)

        iv_temp = findViewById(R.id.iv_temp)
        iv_temp2 = findViewById(R.id.iv_temp2)

        mPosArr = intent.getIntegerArrayListExtra("location")

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return MyFragment()
            }

            override fun getCount(): Int {
                return 2
            }

        }
        doReadyJob()
        computerAnimationParams()
        showEnterAnim()

        var metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val screenHigh = getScreentHeight(this)
        val params = iv_temp2!!.layoutParams
        iv_temp2!!.scaleY = screenHigh * 1f / params.height
        iv_temp2!!.scaleX = screenHigh * 1f / params.height

        iv_temp2?.postDelayed( {
            val h = findViewById<FrameLayout>(R.id.fl_root).measuredHeight
            KLog.debug("Test---> ${screenHigh * 1f / params.height}  ${h * 1f / params.height}  ${h} ${screenHigh} ${params.height} ${getStatusBarHeight()}")
        },1000)
    }


    var mPosArr = ArrayList<Int>()
    var mOriginWidth = 0
    var mRadio = 0f
    var mTransition = arrayOf(0f, 0f)


    private fun showEnterAnim() {
        val enterAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(400)
        enterAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            iv_temp?.setAlpha(1f)
            iv_temp?.setScaleX(1 + (mRadio - 1) * value)
            iv_temp?.setScaleY(1 + (mRadio - 1) * value)
            iv_temp?.setTranslationX(mTransition[0] * value)
            iv_temp?.setTranslationY(mTransition[1] * value)
            iv_temp?.invalidate()
            if (value == 1f) {
            }
        }
        enterAnimator.start()
    }

    fun getScreentHeight(activity: Activity): Int {
        var heightPixels: Int
        val w = activity.windowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)
        // since SDK_INT = 1;
        heightPixels = metrics.heightPixels
        // includes window decorations (statusbar bar/navigation bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                heightPixels = Display::class.java
                        .getMethod("getRawHeight").invoke(d) as Int
            } catch (ignored: Exception) {
            }
        else if (Build.VERSION.SDK_INT >= 17)
            try {
                val realSize = android.graphics.Point()
                Display::class.java.getMethod("getRealSize",
                        android.graphics.Point::class.java).invoke(d, realSize)
                heightPixels = realSize.y
            } catch (ignored: Exception) {
            }
        return heightPixels
    }

    private fun getStatusBarHeight(): Int {
        var height = 0
        var resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height += getResources().getDimensionPixelSize(resourceId);
        }
        KLog.debug("StatusBarHeight--->$height")
        return height
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

        val screenHigh = metrics.heightPixels - getStatusBarHeight()
        val screenWidth = metrics.widthPixels

        val imageHeight = mPosArr.get(3) - mPosArr.get(1)
        val imageWidth = mPosArr.get(2) - mPosArr.get(0)

        val radioWidth = screenWidth * 1.0f / imageWidth
        val radioHeight = screenHigh * 1.0f / imageHeight
        // 获取放大系数
        mRadio = Math.min(radioHeight, radioWidth)

        // 计算位移距离，屏幕中心与图片中心的距离，
        mTransition[0] = (screenWidth / 2 - (mPosArr.get(0) + imageWidth / 2)).toFloat()
        mTransition[1] = (screenHigh / 2 - (mPosArr.get(1) - getStatusBarHeight() + imageHeight / 2)).toFloat()
        KLog.debug("Test---> dy${mTransition[1]}  screenH$screenHigh ")
    }

    /**
     * 动画前的准备工作
     */
    private fun doReadyJob() {
        // 需要对图片的宽度进行调整
        (iv_temp?.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.width = mPosArr[2] - mPosArr[0]
            it.height = mPosArr[3] - mPosArr[1]
            it.leftMargin = mPosArr[0]
            it.topMargin = mPosArr[1] - getStatusBarHeight()
            iv_temp?.setLayoutParams(it)
            return@let
        }
    }

}


