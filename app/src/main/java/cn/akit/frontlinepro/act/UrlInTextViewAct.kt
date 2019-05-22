package cn.akit.frontlinepro.act

import android.text.Editable
import android.text.TextWatcher
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import com.socks.library.KLog
import kotlinx.android.synthetic.main.act_url_in_text_view.*
import java.util.regex.Pattern

/**
 * Created by chenYuXuan on 2019/4/29.
 * email : southxvii@163.com
 */
class UrlInTextViewAct : BaseAct() {


    override fun init() {
        et_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                KLog.debug("Test---> s$s")

                containsLink(s.toString())
            }

        })
    }

    fun containUrl(string: String) {
        val URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$"
        val p = Pattern.compile(URL_REGEX)
        val m = p.matcher(string)//replace with string to compare
        if (m.find()) {

        }
    }

    fun containsLink(input: String):Array<String>? {
        val parts = input.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (item in parts) {
            if (android.util.Patterns.WEB_URL.matcher(item).matches()) {
               KLog.debug("Test---> $item")
            }
        }
        return parts
    }

    override fun provideViewRes(): Int {
        return R.layout.act_url_in_text_view
    }

}