package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Card implements Serializable {  // Implement Serializable
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String amount;
    public String imageUri;  // Store the URI of the image in the database
    private boolean isRedeemed = false;

    public boolean isRedeemed() { return isRedeemed; }
    public void setRedeemed(boolean redeemed) { isRedeemed = redeemed; }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }


}
