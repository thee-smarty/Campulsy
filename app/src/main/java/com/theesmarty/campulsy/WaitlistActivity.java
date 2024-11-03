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

        // TODO: 11/3/24 if userStat vlaue is 2 then Show the user waitlist info and invitation link

        // TODO: 11/3/24 if user submits the  form make the shared preferences userStat value as 2
    }
}