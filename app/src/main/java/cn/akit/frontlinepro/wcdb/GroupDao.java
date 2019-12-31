package cn.akit.frontlinepro.wcdb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by chenYuXuan on 2019/9/19.
 * email : southxvii@163.com
 */
@Dao
public interface GroupDao {
    @Query("SELECT * FROM `group`")
    List<Group> getAll();

    @Query("SELECT * FROM `group` WHERE groupId = :groupId")
    Group getById(int groupId);

    @Insert
    void insert(Group... groups);


}