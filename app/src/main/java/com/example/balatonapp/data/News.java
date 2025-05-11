package com.example.balatonapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class News {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String date;
    private String imageName;  // ÚJ: képfájl neve
    private String url;        // ÚJ: hivatkozás az eredeti cikkre

    public News(String title, String description, String date, String imageName, String url) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageName = (imageName == null || imageName.isEmpty()) ? "placeholder" : imageName;
        this.url = url;
    }

    // Getterek és setterek
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getImageName() { return imageName; }
    public String getUrl() { return url; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setUrl(String url) { this.url = url; }
    public String getExcerpt() { return description; }
    public String getLink() { return url; }
}
