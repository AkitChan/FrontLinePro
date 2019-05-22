package cn.akit.frontlinepro.act

import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import kotlinx.android.synthetic.main.act_encrypt.*
import java.net.URLDecoder

/**
 * Created by chenYuXuan on 2019/4/29.
 * email : southxvii@163.com
 */
class EncryptAct: BaseAct() {



    override fun init() {
        tv_text.setOnClickListener {
            val string = "U3VLTkh1TTBURjMyeElxNmJ0YWUwd0tqdE81VlJyM1E3V1JZT21POU44YTlZMWpjVklPaDVjRHFOOU02VVJhUDVseDNLQlFqbUtJQzdHc0JBZ3MzUFE9PQ=="
            val result = URLDecoder.decode(string)
            tv_text.text = result
        }
    }

    override fun provideViewRes(): Int {
        return R.layout.act_encrypt
    }

}