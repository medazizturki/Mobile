package com.example.blogappll.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.blogappll.Entity.Participant;
import java.util.List;

@Dao
public interface ParticipantDao {
    @Insert
    void insertParticipant(Participant participant);

    @Query("SELECT * FROM Participant")
    List<Participant> getAllParticipants();
}
