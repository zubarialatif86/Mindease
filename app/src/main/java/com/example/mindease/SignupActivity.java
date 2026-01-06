package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signupBtn = findViewById(R.id.signupBtn);
        TextView loginText = findViewById(R.id.loginText);

        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
            finish();
        });

        loginText.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class))
        );
    }
}
