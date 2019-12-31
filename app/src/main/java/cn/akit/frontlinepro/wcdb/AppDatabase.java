package cn.akit.frontlinepro.wcdb;

/**
 * Created by chenYuXuan on 2019/9/19.
 * email : southxvii@163.com
 */


import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by johnwhe on 2017/7/12.
 */

@Database(entities = {User.class,Group.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract GroupDao groupDao();
}