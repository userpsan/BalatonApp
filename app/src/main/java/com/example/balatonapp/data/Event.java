package com.example.balatonapp.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String date;
    private String location;
    private String imageName;

    // Konstruktor Room számára
    public Event(String title, String description, String date, String location, String imageName) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.imageName = imageName;
    }

    // Használható, ha id is ismert (pl. debughoz, listakezeléshez)
    @Ignore
    public Event(int id, String title, String description, String date, String location, String imageName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.imageName = imageName;
    }

    // Üres konstruktor Firestore-hoz
    @Ignore
    public Event() {
        this.title = "";
        this.description = "";
        this.date = "";
        this.location = "";
        this.imageName = "placeholder";
    }

    // --- Getterek ---
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getImageName() {
        return imageName;
    }

    // --- Setterek Room/Firebase miatt ---
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
