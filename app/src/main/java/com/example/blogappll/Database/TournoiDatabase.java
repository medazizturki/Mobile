package com.example.blogappll.Database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.blogappll.Dao.TournoiDao;
import com.example.blogappll.Entity.Tournoi;

@Database(entities = {Tournoi.class}, version = 1)
public  abstract class TournoiDatabase extends  RoomDatabase  {
    private static volatile TournoiDatabase INSTANCE;

    public abstract TournoiDao TournoiDao();
}
