package cn.akit.frontlinepro.act

import android.os.Environment
import cn.akit.frontlinepro.common.base.BaseAct
import cn.akit.frontlinepro.model.CityBean
import cn.akit.frontlinepro.util.*
import com.github.promeg.pinyinhelper.Pinyin
import com.google.gson.Gson
import com.socks.library.KLog
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * 翻译
 *
 *
 * Created by chenYuXuan on 2019/4/29.
 * email : southxvii@163.com
 */
class EncryptAct : BaseAct() {


    override fun init() {

        translateCity()
    }

    fun translateCity() {
        val s = resources.openRawResource(cn.akit.frontlinepro.R.raw.chinese_country_json)
        val input = InputStreamReader(s, "UTF-8")
        val sb = StringBuffer()
        for (line in input.readLines()) {
            sb.append(line)
        }
        s.close()

//        val json = JSONObject(sb.toString())
//        json.getJSONArray()

        KLog.debug("Test--->" + sb.toString())
        val gson = Gson()
        val cityBean = gson.fromJson<CityBean>(sb.toString(), CityBean::class.java)

        val bean = CityBean()
        val list = LinkedList<CityBean.City>()
        bean.data = list

        var size = cityBean.data.size
        for (i in 0 until size) {
            val datum = cityBean.data[i]
            var translateCallback: TranslateCallback = TranslateCallback {
                val item = CityBean.City()
                item.countryCode = datum.countryCode
                item.phoneCode = datum.phoneCode
                item.countryName = it
                item.countryPinyin = Pinyin.toPinyin(it, " ")
                list.add(item)

                if (i == 2) {
                    KLog.debug("Test---> " + bean)

                    val cacheDir = File(DIR)
                    if (!cacheDir.exists()) {
                        cacheDir.mkdir()
                    }
                    val file = File(cacheDir, "en_country_json.txt")
                    try {
                        if (!file.exists()) {
                            file.createNewFile()
                        }
                        val bw = BufferedWriter(FileWriter(file))
                        bw.write(Gson().toJson(bean))
                        bw.close()
                        println("写入完成" + cacheDir.name + " " + file.getName())

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            TranslateUtil().translate(this, "auto", "vi", datum.countryName, translateCallback)
        }

    }
    private val DIR = Environment.getExternalStorageDirectory().path


    fun translateLanguage() {
        val s = resources.openRawResource(cn.akit.frontlinepro.R.raw.json)
        val input = InputStreamReader(s, "gbk")
        val reader = BufferedReader(input)
        val sb = StringBuffer()
        for (line in input.readLines()) {
            sb.append(line)
        }
        val map = XmlpullUtil.parse(sb.toString())
        s.close()

        val newMap = HashMap<String, String>()
        for (mutableEntry in map) {
            newMap.put(mutableEntry.key, mutableEntry.value)
        }

        val enList = ArrayList<ValueModel>()
        for (key in newMap.keys) {
            val value = newMap[key]?.replace("Tother Chat", "AAAAAAAAAA")
            var translateCallback: TranslateCallback = TranslateCallback {
                // result是翻译结果，在这里使用翻译结果，比如使用对话框显示翻译结果
                enList.add(ValueModel(key, it))
                if (enList.size == newMap.size) {
                    GlobalizationUtil().put("vi", enList)
                    GlobalizationUtil.createValuesStringsXml()
                }
            }

            TranslateUtil().translate(this, "auto", "vi", value, translateCallback)
        }
    }

    override fun provideViewRes(): Int {
        return cn.akit.frontlinepro.R.layout.act_encrypt
    }


}