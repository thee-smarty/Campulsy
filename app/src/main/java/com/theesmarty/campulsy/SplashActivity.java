package com.theesmarty.campulsy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser currentUser;
    SharedPreferences statShare;
    int userStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Initialize SharedPreferences
        statShare = getSharedPreferences("Stat", MODE_PRIVATE);
        userStat = statShare.getInt("sharedInt", 1);

        // Check user authentication
        if (currentUser != null) {
            new Handler().postDelayed(() -> {
                Intent in = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }, 2000);
        } else {
            // Check user status
            new Handler().postDelayed(() -> {
                Intent in;
                if (userStat == 1) {
                    in = new Intent(SplashActivity.this, LoginActivity.class);
                } else if (userStat == 2) {
                    in = new Intent(SplashActivity.this, WaitlistActivity.class);
                } else {
                    in = new Intent(SplashActivity.this, LoginActivity.class); // Default fallback
                }
                startActivity(in);
                finish();
            }, 2000);
        }
    }
}
