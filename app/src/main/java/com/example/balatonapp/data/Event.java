package com.example.balatonapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String date;
    public String location;
    public String imageName;

    // Room által használt konstruktor
    public Event(String title, String description, String date, String location, String imageName) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.imageName = imageName;
    }

    // Üres konstruktor, ha kell más célokra – meg kell jelölni @Ignore-val
    @Ignore
    public Event() {
        this.title = "";
        this.description = "";
        this.date = "";
        this.location = "";
        this.imageName = "placeholder";
    }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getImageName() {
        return imageName;
    }

}
