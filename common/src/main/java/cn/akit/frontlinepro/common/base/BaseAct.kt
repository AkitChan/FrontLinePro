package cn.akit.frontlinepro.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by chenYuXuan on 2019/4/22.
 * email : southxvii@163.com
 */
abstract class BaseAct : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideViewRes())
        init()
    }

    abstract fun provideViewRes():Int
    abstract fun init()

}