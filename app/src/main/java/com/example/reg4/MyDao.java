package com.example.reg4;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    public void addUser(User user);

    @Query("select * from users3 where email IN (:userId1) AND password IN (:userId2)")
    public  List<User> getuser(String userId1, String userId2);

    @Query("select * from users3 where email = (:userId1) AND password =(:userId2)")
    public  List<User> getuser1(String userId1, String userId2);

    @Update
    public void Updateuser(User user);
}

