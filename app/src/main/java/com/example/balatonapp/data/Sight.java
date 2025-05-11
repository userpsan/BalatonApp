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

    // Üres konstruktor Firestore-nak
    public Sight() {
    }

    // Teljes konstruktor
    public Sight(String name, String description, String location, String imageName) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageName = imageName;
    }

    // GETTEREK
    public int getId() {
        return id;
    }

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

    // SETTEREK (Room miatt szükségesek)
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
