package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Set click listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    return;
                }

                // Dummy login validation
                if (email.equals("test@example.com") && password.equals("password")) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Navigate to the main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for register link
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}