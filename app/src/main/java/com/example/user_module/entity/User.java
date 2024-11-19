package com.example.user_module.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "confirmationCode")
    public String confirmationCode;

    @ColumnInfo(name = "confirmed")
    public boolean confirmed;



    public User(String email, String password, String confirmationCode) {
        this.email = email;
        this.password = password;
        this.confirmationCode = confirmationCode;
        this.confirmed = false; // Default to false until confirmed
    }

}
