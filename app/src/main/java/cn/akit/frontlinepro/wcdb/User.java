package cn.akit.frontlinepro.wcdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by chenYuXuan on 2019/9/19.
 * email : southxvii@163.com
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    public String firstName;
    public String lastName;
}