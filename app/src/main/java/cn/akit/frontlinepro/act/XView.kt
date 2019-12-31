package cn.akit.frontlinepro.act

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.View
import cn.akit.frontlinepro.act.kt.dp2px

/**
 * 2019/11/26.
 * Description
 *
 *
 */
class XView(context: Context?) : View(context) {

    val path = Path()

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val radius = dp2px(30f)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawLine()
//        canvas?.drawCircle

        //重叠的区域的处理方式,EVEN_ODD:重叠次数Double就镂空
        path.fillType = Path.FillType.EVEN_ODD

        canvas?.drawPath(path,paint)
    }

    //测量尺寸发生变化
    //注意区分绘制与修改布局
    //在View尺寸发生变化时触发,初始化会调用
    //CCW (Counter-Clockwise) 逆时针 CW 顺时针
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.addCircle(width / 2 as Float, height / 2 as Float, radius, Path.Direction.CCW)
        path.addCircle(width / 2 as Float, height / 2 as Float, radius * 2, Path.Direction.CCW)
    }


    val pathMeasure = PathMeasure(path,false)
//    pathMeasure.length 所走路径的长度
}