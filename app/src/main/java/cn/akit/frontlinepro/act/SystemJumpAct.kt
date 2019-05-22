package cn.akit.frontlinepro.act

import android.os.Build
import android.view.View
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import cn.akit.frontlinepro.common.utils.SystemJumpUtil

/**
 * Created by chenYuXuan on 2019/4/24.
 * email : southxvii@163.com
 */
class SystemJumpAct : BaseAct() {
    override fun provideViewRes(): Int {
        return R.layout.act_system_jump
    }

    fun toSettings(view: View) {
        SystemJumpUtil.toSettings(this)
    }

    fun toAppNotification(view: View) {
        SystemJumpUtil.toAppNotification(this)
    }

    fun toPermission(view: View) {
        SystemJumpUtil.toPermission(this)
    }

    fun toNotificationChannel(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SystemJumpUtil.toNotificationChannelSetting(this,this.packageName)
        }else{

        }
    }

    override fun init() {

    }
}
