package com.example.balatonapp.firestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FirestoreService {

    private final FirebaseFirestore db;
    private final String userId;

    public FirestoreService() {
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private CollectionReference getUserCategoryCollection(String category) {
        return db.collection("favorites")
                .document(userId)
                .collection(category);
    }

    public void addFavorite(String category, String itemId) {
        getUserCategoryCollection(category)
                .document(itemId)
                .set(new HashMap<String, Object>() {{
                    put("favorited", true);
                }});
    }

    public void removeFavorite(String category, String itemId) {
        getUserCategoryCollection(category)
                .document(itemId)
                .delete();
    }

    public void isFavorite(String category, String itemId, Callback<Boolean> callback) {
        getUserCategoryCollection(category)
                .document(itemId)
                .get()
                .addOnSuccessListener(doc -> callback.onResult(doc.exists()))
                .addOnFailureListener(e -> callback.onResult(false));
    }

    public void getFavorites(String category, Callback<Set<String>> callback) {
        getUserCategoryCollection(category)
                .get()
                .addOnSuccessListener(snapshot -> {
                    Set<String> ids = new HashSet<>();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        ids.add(doc.getId());
                    }
                    callback.onResult(ids);
                })
                .addOnFailureListener(e -> callback.onResult(new HashSet<>()));
    }
}
