package com.example.balatonapp.data;


public class FavoriteItem {

    private String itemId;
    private String type;
    private String title;
    private String location;
    private String description;
    private String imageName;
    private String note;

    public FavoriteItem() {}


    public FavoriteItem( String itemId, String type,
                        String title, String location, String description,
                        String imageName, String note) {
        this.itemId = itemId;
        this.type = type;
        this.title = title;
        this.location = location;
        this.description = description;
        this.imageName = imageName;
        this.note = note;
    }
    public String getItemId() {
        return itemId;
    }
    public String getType() {
        return type;
    }
    public String getTitle() {
        return title;
    }
    public String getLocation() {
        return location;
    }
    public String getDescription() {
        return description;
    }
    public String getImageName() {
        return imageName;
    }
    public String getNote() {
        return note;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
