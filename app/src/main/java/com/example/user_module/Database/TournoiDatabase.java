package com.example.user_module.Database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.user_module.Dao.TournoiDao;
import com.example.user_module.entity.Tournoi;

@Database(entities = {Tournoi.class}, version = 1)
public  abstract class TournoiDatabase extends  RoomDatabase  {
    private static volatile TournoiDatabase INSTANCE;

    public abstract TournoiDao TournoiDao();
}
