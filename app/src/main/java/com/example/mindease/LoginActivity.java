package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        CardView loginBtn = findViewById(R.id.loginBtn);
        TextView signupText = findViewById(R.id.signupText);

        // Note: Password visibility toggle is handled automatically 
        // by TextInputLayout in activity_login.xml using app:passwordToggleEnabled="true"

        if (loginBtn != null) {
            loginBtn.setOnClickListener(v -> {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.equals("zubarialatif86@gmail.com") && password.equals("zubi@112233")) {
                    Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("MindEasePrefs", MODE_PRIVATE)
                            .edit()
                            .putString("user_id", "8ve9Td3MBLbCZhG68w91jNHouEo1")
                            .apply();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (signupText != null) {
            signupText.setOnClickListener(v ->
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class))
            );
        }
    }
}
