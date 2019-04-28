package cn.akit.frontlinepro.common.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.annotation.RequiresApi




/**
 * 系统页面跳转
 *
 * Created by chenYuXuan on 2019/4/23.
 * email : southxvii@163.com
 */
class SystemJumpUtil {
    companion object {

        @RequiresApi(26)
        @JvmStatic
        fun toNotificationChannelSetting(context: Context, channelID: String) {
            try {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        .putExtra(Settings.EXTRA_CHANNEL_ID, channelID)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                toSettings(context)
            }
        }


        @JvmStatic
        fun toSettings(context: Context) {
            var intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS";
                intent.data = Uri.fromParts("package", context.packageName, null);
            } else if (Build.VERSION.SDK_INT <= 8) {
                intent.action = Intent.ACTION_VIEW;
                intent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                intent.putExtra("com.android.settings.ApplicationPkgName", context.packageName);
            }
            context.startActivity(intent);
        }


        /**
         * 跳转到权限设置界面
         */
        @JvmStatic
        fun toAppNotification(context: Context) {

            // vivo 点击设置图标>加速白名单>我的app
            //      点击软件管理>软件管理权限>软件>我的app>信任该软件
            var appIntent = context.packageManager.getLaunchIntentForPackage("com.iqoo.secure")
            if (appIntent != null) {
                context.startActivity(appIntent)
                return
            }

            // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
            //      点击权限隐私>自启动管理>我的app
            appIntent = context.packageManager.getLaunchIntentForPackage("com.oppo.safe")
            if (appIntent != null) {
                context.startActivity(appIntent)
                return
            }

            val intent = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra("app_package", context.packageName)
                intent.putExtra("app_uid", context.applicationInfo.uid)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", context.packageName, null)
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.action = Intent.ACTION_VIEW
                intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
                intent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
            }
            context.startActivity(intent)
        }


        @JvmStatic
        fun toPermission(context: Context) {
            var model = android.os.Build.MODEL // 手机型号
            var release = android.os.Build.VERSION.RELEASE // android系统版本号
            var brand = Build.BRAND//手机厂商
            if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                gotoMiuiPermission(context);//小米
            } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                gotoMeizuPermission(context);
            } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                gotoHuaweiPermission(context);
            } else {
                context.startActivity(getAppDetailSettingIntent(context));
            }

        }

        /**
         * 跳转到miui的权限管理页面
         */
        private fun gotoMiuiPermission(context: Context) {
            try { // MIUI 8
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
                localIntent.putExtra("extra_pkgname", context.getPackageName())
                context.startActivity(localIntent)
            } catch (e: Exception) {
                try { // MIUI 5/6/7
                    val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                    localIntent.putExtra("extra_pkgname", context.getPackageName())
                    context.startActivity(localIntent)
                } catch (e1: Exception) { // 否则跳转到应用详情
                    context.startActivity(getAppDetailSettingIntent(context))
                }

            }

        }

        /**
         * 跳转到魅族的权限管理系统
         */
        private fun gotoMeizuPermission(context: Context) {
            try {
                val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.putExtra("packageName", context.getPackageName())
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                context.startActivity(getAppDetailSettingIntent(context))
            }

        }

        /**
         * 华为的权限管理页面
         */
        private fun gotoHuaweiPermission(context: Context) {
            try {
                val intent = Intent()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")//华为权限管理
                intent.component = comp
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                context.startActivity(getAppDetailSettingIntent(context))
            }

        }

        /**
         * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
         *
         * @return
         */
        private fun getAppDetailSettingIntent(context: Context): Intent {
            val localIntent = Intent()
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts("package", context.getPackageName(), null)
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.action = Intent.ACTION_VIEW
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName())
            }
            return localIntent
        }
    }
}