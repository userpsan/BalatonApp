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
            userId = "anonymous";
        }
    }

    private CollectionReference getFavoritesCollection() {
        return db.collection("favorites_" + userId);
    }

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

    public void removeFavorite(String category, String itemId) {
        if (category == null || itemId == null) return;

        getFavoritesCollection()
                .document(itemId)
                .delete();
    }

    public void updateNote(String itemId, String newNote) {
        if (itemId == null || newNote == null) return;

        getFavoritesCollection()
                .document(itemId)
                .update("note", newNote, "timestamp", System.currentTimeMillis());
    }

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
