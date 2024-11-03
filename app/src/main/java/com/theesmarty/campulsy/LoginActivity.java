package com.theesmarty.campulsy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Spinner collegePicker;
    TextView otherSelectedInfo;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        collegePicker = findViewById(R.id.pickCollege);
        otherSelectedInfo = findViewById(R.id.otherSelectedInfo);
        loginButton = findViewById(R.id.loginButton);

        String[] colleges = {"Select Campus" , "VR Siddhartha Engineering College", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colleges);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegePicker.setAdapter(adapter);

        collegePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                String selectedCollege = colleges[position];


                if (selectedCollege.equals("Select Campus")) {
                    otherSelectedInfo.setVisibility(View.GONE);
                    loginButton.setVisibility(View.GONE);
                }else if (selectedCollege.equals("Other")) {
                    otherSelectedInfo.setVisibility(View.VISIBLE);
                    otherSelectedInfo.setText("Join your campus waitlist. You'll be notified as soon as your campus is added.");
                    loginButton.setVisibility(View.VISIBLE);
                    loginButton.setText("Join Your Campus");
                } else {
                    otherSelectedInfo.setVisibility(View.VISIBLE);
                    otherSelectedInfo.setText("Note : Choose only college domain mail while proceeding with login");
                    loginButton.setVisibility(View.VISIBLE);
                    loginButton.setText("Login to Proceed");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // TODO: 11/3/24 onButton click if the campus selected is !="other" initiate oAuth for login

        // TODO: 11/3/24 process the login request with the domain mail and take actions

        // TODO: 11/3/24 onButton click if the campus selected =="other" Move to Waitlist Activity

    }
}