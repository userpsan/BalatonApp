package com.example.balatonapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private final String date;
    private final String location;
    private final String imageName;

    public Event(String title, String description, String date, String location, String imageName) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.imageName = imageName;
    }


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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
