package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_signup);

            // Use generic View to avoid ClassCastException (CardView vs Button)
            View signupBtn = findViewById(R.id.signupBtn);
            View loginText = findViewById(R.id.loginText);

            if (signupBtn != null) {
                signupBtn.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Close Signup screen after success
                    } catch (Exception e) {
                        Toast.makeText(this, "Navigation failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (loginText != null) {
                loginText.setOnClickListener(v -> {
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
