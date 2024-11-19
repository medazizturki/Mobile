package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.user_module.entity.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Insert
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM Card")
    List<Card> getAllCards();

    @Query("SELECT * FROM Card WHERE id = :id")
    Card getCardById(int id);

    @Query("SELECT * FROM card WHERE description = :description LIMIT 1")
    Card getCardByDescription(String description);


}

