package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.user_module.entity.Participant;

import java.util.List;

@Dao
public interface ParticipantDao {
    @Insert
    void insertParticipant(Participant participant);

    @Query("SELECT * FROM Participant")
    List<Participant> getAllParticipants();
}
