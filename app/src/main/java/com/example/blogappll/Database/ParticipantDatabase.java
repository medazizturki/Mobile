package com.example.blogappll.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.blogappll.Dao.ParticipantDao;
import com.example.blogappll.Entity.Participant;

@Database(entities = {Participant.class}, version = 1)
public abstract class ParticipantDatabase extends RoomDatabase {
    private static ParticipantDatabase instance;

    public abstract ParticipantDao participantDao();

    public static synchronized ParticipantDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ParticipantDatabase.class,
                    "participant_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
