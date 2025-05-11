package com.example.balatonapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sight_table")
public class Sight {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String location;
    private String imageName;

    // Konstruktor
    public Sight(String name, String description, String location, String imageName) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageName = imageName;
    }

    // Getterek – EZEK hiányoztak
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getImageName() {
        return imageName;
    }

    public int getId() {
        return id;
    }

    // Setterek (ha kell Room-hoz)
    public void setId(int id) {
        this.id = id;
    }
}
