package com.example.user_module;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.user_module.Dao.UserDao;
import com.example.user_module.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
