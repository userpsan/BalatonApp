package com.example.balatonapp.firestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FirestoreService {

    private final FirebaseFirestore db;
    private final String userId;

    public FirestoreService() {
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            userId = "anonymous"; // fallback, de nem fog működni rendesen bejelentkezés nélkül
        }
    }

    // Felhasználónként külön kollekció (pl. favorites_uid12345)
    private CollectionReference getFavoritesCollection() {
        return db.collection("favorites_" + userId);
    }

    // Kedvenc hozzáadása adott kategóriában (pl. "event" vagy "sight")
    public void addFavorite(String category, String itemId, Map<String, Object> itemData) {
        if (category == null || itemId == null || itemData == null) return;

        Map<String, Object> data = new HashMap<>(itemData);
        data.put("userId", userId);
        data.put("type", category);
        data.put("itemId", itemId);
        data.put("timestamp", System.currentTimeMillis());

        getFavoritesCollection()
                .document(itemId)
                .set(data);
    }

    // Kedvenc törlése
    public void removeFavorite(String category, String itemId) {
        if (category == null || itemId == null) return;

        getFavoritesCollection()
                .document(itemId)
                .delete();
    }

    // Megnézi, hogy az adott elem kedvenc-e
    public void isFavorite(String category, String itemId, Callback<Boolean> callback) {
        if (category == null || itemId == null) {
            callback.onResult(false);
            return;
        }

        getFavoritesCollection()
                .document(itemId)
                .get()
                .addOnSuccessListener(snapshot -> callback.onResult(snapshot.exists()))
                .addOnFailureListener(e -> callback.onResult(false));
    }

    // Az adott típusú kedvencek ID-jait adja vissza (pl. minden "sight" vagy "event" típus)
    public void getFavorites(String category, Callback<Set<String>> callback) {
        getFavoritesCollection()
                .whereEqualTo("type", category)
                .get()
                .addOnSuccessListener(snapshot -> {
                    Set<String> ids = new HashSet<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        String id = doc.getString("itemId");
                        if (id != null) {
                            ids.add(id);
                        }
                    }
                    callback.onResult(ids);
                })
                .addOnFailureListener(e -> callback.onResult(new HashSet<>()));
    }
}
