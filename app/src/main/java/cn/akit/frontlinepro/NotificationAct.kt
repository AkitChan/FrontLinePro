package cn.akit.frontlinepro

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import androidx.core.app.NotificationCompat
import cn.akit.frontlinepro.common.base.BaseAct

/**
 * Created by chenYuXuan on 2019/4/24.
 * email : southxvii@163.com
 */
class NotificationAct : BaseAct() {
    override fun provideViewRes(): Int {
        return R.layout.act_notification
    }

    fun showBanner(view: View) {
        var manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var builder = NotificationCompat.Builder(this)
        builder.setContentTitle("横幅通知")
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限")
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher))
        var intent = Intent(this, MainAct::class.java)
        var pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        builder.setContentIntent(pIntent)
        builder.setFullScreenIntent(pIntent, true)
        builder.setAutoCancel(true)
        var notification = builder.build()
        manager.notify(1, notification)
    }

    override fun init() {

    }
}
