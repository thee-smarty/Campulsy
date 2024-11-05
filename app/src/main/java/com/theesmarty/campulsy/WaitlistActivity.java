package com.theesmarty.campulsy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WaitlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitlist);
        // TODO: 11/3/24 Create a form for new Campus

        // TODO: 11/3/24 if userStat value is 1 then allow user to fill the form

        // TODO: 11/3/24 if userStat value is 2 then Show the user waitlist info and invitation link

        // TODO: 11/5/24 if user fills all he details and click submit, show him alert box with info to join waitlist

        // TODO: 11/3/24 if user submits the  form make the shared preferences userStat value as 2

        // TODO: 11/5/24  Generate a link to invite friends for quick adding of campus
    }
}