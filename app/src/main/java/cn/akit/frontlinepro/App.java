package cn.akit.frontlinepro;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by chenYuXuan on 2019/4/13.
 * email : southxvii@163.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        init();
    }

    private void init() {
        initNotification();
    }

    private void initNotification() {
//        NotificationHelper.init(this);
//        NotificationHelper.createNotificationChannel(this,true,true,0,null);
    }

}
