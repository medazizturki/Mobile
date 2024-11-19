package com.example.blogappll.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Participant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String teamName;
    private String leaderName;
    private int numberOfMembers;

    // Constructor
    public Participant(String teamName, String leaderName, int numberOfMembers) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.numberOfMembers = numberOfMembers;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }
}
