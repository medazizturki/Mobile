package com.example.blogappll.Dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blogappll.Entity.Tournoi;

import java.util.List;
@Dao
public interface TournoiDao {

    @Insert
    void insert(Tournoi Tournoi);

    @Update
    void update(Tournoi Tournoi);

    @Delete
    void delete(Tournoi Tournoi);

    @Query("SELECT * FROM Tournoi")
    List<Tournoi> getAllTournois();

    @Query("SELECT * FROM Tournoi WHERE id = :id LIMIT 1")
    Tournoi getTournoiById(int id);

}
