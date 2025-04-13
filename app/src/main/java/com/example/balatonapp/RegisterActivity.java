package com.example.balatonapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private static final int SECRET_KEY = 2102;
    private static final String PREF_KEY = Objects.requireNonNull(RegisterActivity.class.getPackage()).toString();

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        int secret = getIntent().getIntExtra("SECRET_KEY", 0);
        if (secret != SECRET_KEY) {
            finish();
        }

        emailEditText = findViewById(R.id.editTextEmailRegistration);
        passwordEditText = findViewById(R.id.editTextPassword);
        passwordAgainEditText = findViewById(R.id.editTextPasswordAgain);
        findViewById(R.id.editTextFullName);

        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        emailEditText.setText(preferences.getString("email", ""));
        passwordEditText.setText(preferences.getString("password", ""));
        passwordAgainEditText.setText(preferences.getString("password", ""));

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.registrationButton).setOnClickListener(this::registration);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    public void registration(android.view.View view) {
        String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";
        String passwordAgain = passwordAgainEditText.getText() != null ? passwordAgainEditText.getText().toString() : "";

        if (!password.equals(passwordAgain)) {
            Toast.makeText(this, "A jelszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(this, "Hiba a regisztráció során.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
