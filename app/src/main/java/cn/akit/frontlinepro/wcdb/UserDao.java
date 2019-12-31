package cn.akit.frontlinepro.wcdb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by chenYuXuan on 2019/9/19.
 * email : southxvii@163.com
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE userId = :userId")
    User getById(int userId);

    @Insert
    void insert(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}