package com.example.blogappll.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Tournoi implements Serializable {  // Implement Serializable
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String prix;
    public String imageUri;  // Store the URI of the image in the database
    public String nombrePlace;  // Added the number of available spots for the tournament
}
