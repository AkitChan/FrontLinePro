@file:JvmName("JavaClass")
@file:JvmMultifileClass

package cn.akit.frontlinepro.act.kt

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by chenYuXuan on 2019/7/8.
 * email : southxvii@163.com
 */


const val bb = "aaa"

@AAAAA(string = "哈哈哈哈")
fun A() {

}


@JvmName("AA")
fun B() {

}

annotation class AAAAA(val string: String) {
}


fun dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
            , dp
            , Resources.getSystem().displayMetrics)
}