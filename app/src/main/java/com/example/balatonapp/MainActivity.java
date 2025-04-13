package com.example.balatonapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int SECRET_KEY = 2102;

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        
        Button loginButton = findViewById(R.id.loginButton);
        Button registrationButton = findViewById(R.id.registrationButton);

        loginButton.setOnClickListener(v -> login());
        registrationButton.setOnClickListener(v -> goToRegistration());
    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Kérlek, add meg az e-mail címed és a jelszavad!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, this::onComplete);
    }

    private void goToRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    private void onComplete(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(this, "Bejelentkezés sikeres!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Hiba a bejelentkezés során: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
