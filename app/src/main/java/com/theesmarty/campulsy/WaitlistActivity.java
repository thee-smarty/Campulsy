package com.theesmarty.campulsy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WaitlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitlist);

        Button inviteButton = findViewById(R.id.inviteButton);

        inviteButton.setOnClickListener(v -> {
            String dynamicLink = "https://theesmarty.in/";

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Join me on Campulsy!\n" + dynamicLink);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Join Campulsy!");
            sendIntent.setType("text/plain");

            startActivity(Intent.createChooser(sendIntent, "Send to"));
        });
    }
}
