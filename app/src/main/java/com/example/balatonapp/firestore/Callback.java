package com.example.balatonapp.firestore;

public interface Callback<T> {
    void onResult(T result);
}
