package cn.akit.frontlinepro.common.utils

import android.annotation.TargetApi
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationCompat
import cn.akit.frontlinepro.common.R

/**
 * Created by chenYuXuan on 2019/4/24.
 * email : southxvii@163.com
 */

/**
 * Created by chenYuXuan on 2019/4/23.
 * email : southxvii@163.com
 */
class NotificationHelper {

    companion object {

        @JvmStatic
        val NEW_MESSAGE_CHANNEL_ID: String
            get() {
                return context.packageName
            }

        @JvmStatic
        val NEW_MESSAGE_CHANNEL_NAME: String
            get() {
                return context.resources.getString(R.string.notification_name)
            }

        @TargetApi(26)
        @JvmStatic
        fun createNotificationChannel(context: Context, sound: Boolean, vibrate: Boolean, soundRes: Int?, longArray: LongArray?) {
            notificationManager.deleteNotificationChannel(NEW_MESSAGE_CHANNEL_ID)
            val notificationChannel = NotificationChannel(NEW_MESSAGE_CHANNEL_ID,
                    NEW_MESSAGE_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(false)
            if (vibrate) {
                notificationChannel.enableVibration(true)
                longArray?.let { longArray ->
                    notificationChannel.vibrationPattern = longArray
                }
            } else {
                notificationChannel.enableVibration(false)
                notificationChannel.vibrationPattern = longArrayOf(0)
            }
            if (sound) {
                soundRes?.let { soundRes ->
                    notificationChannel.setSound(Uri.parse("android.resource://" + context.packageName + "/" + soundRes), null)
                }
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }


        fun initBuilder(builder: NotificationCompat.Builder) {

        }


        @JvmStatic
        val newMessageBuilder: NotificationCompat.Builder
            get() {
                return NotificationCompat.Builder(context, NEW_MESSAGE_CHANNEL_ID);
            }

        @JvmStatic
        val context: Context
            get() {
                return context_!!
            }

        private var context_: Application? = null

        @JvmStatic
        fun init(application: Application) {
            context_ = application
        }

        @JvmStatic
        val notificationManager: NotificationManager
            get() {
                return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
    }
}