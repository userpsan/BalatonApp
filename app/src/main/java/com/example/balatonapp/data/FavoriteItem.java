package com.example.balatonapp.data;

import java.util.Date;

public class FavoriteItem {

    private String userId;
    private String itemId;         // Pl. sight.getImageName() vagy event.id
    private String type;           // "sight" vagy "event"
    private String title;          // Cím / név
    private String location;
    private String description;
    private String imageName;
    private String note;           // Felhasználói megjegyzés
    private long timestamp;        // Mentés időbélyege (millisec)

    // Üres konstruktor Firestore-nak
    public FavoriteItem() {
    }

    public FavoriteItem(String userId, String itemId, String type,
                        String title, String location, String description,
                        String imageName, String note, long timestamp) {
        this.userId = userId;
        this.itemId = itemId;
        this.type = type;
        this.title = title;
        this.location = location;
        this.description = description;
        this.imageName = imageName;
        this.note = note;
        this.timestamp = timestamp;
    }

    // --- Getterek ---
    public String getUserId() {
        return userId;
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

    public long getTimestamp() {
        return timestamp;
    }

    // Visszaalakítva olvasható Date-ként (ha kell megjelenítéshez)
    public Date getDate() {
        return new Date(timestamp);
    }

    // --- Setterek (Firestore számára) ---
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
