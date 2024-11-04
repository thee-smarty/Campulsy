package com.theesmarty.campulsy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private Spinner collegePicker;
    private TextView otherSelectedInfo;
    private Button loginButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 100;
    private String collegeDomain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        collegePicker = findViewById(R.id.pickCollege);
        otherSelectedInfo = findViewById(R.id.otherSelectedInfo);
        loginButton = findViewById(R.id.loginButton);

        String[] colleges = {"Select Campus", "VR Siddhartha Engineering College", "Other"};
        String[] domains = {"None", "vrsec.ac.in", "None"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colleges);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegePicker.setAdapter(adapter);

        // Spinner item selection listener
        collegePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collegeDomain = domains[position];

                if (colleges[position].equals("Select Campus")) {
                    otherSelectedInfo.setVisibility(View.GONE);
                    loginButton.setVisibility(View.GONE);
                } else if (colleges[position].equals("Other")) {
                    otherSelectedInfo.setVisibility(View.VISIBLE);
                    otherSelectedInfo.setText("Join your campus waitlist. You'll be notified as soon as your campus is added.");
                    loginButton.setVisibility(View.VISIBLE);
                    loginButton.setText("Join Your Campus");
                } else {
                    otherSelectedInfo.setVisibility(View.VISIBLE);
                    otherSelectedInfo.setText("Note: Use only college domain mail while proceeding with login.");
                    loginButton.setVisibility(View.VISIBLE);
                    loginButton.setText("Proceed to Login");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Configure Google Sign-In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclientid))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Button click functionality
        loginButton.setOnClickListener(v -> {
            if ("Other".equals(colleges[collegePicker.getSelectedItemPosition()])) {
                // If "Other" is selected, go to the Waitlist Activity
                Intent waitlistIntent = new Intent(LoginActivity.this, WaitlistActivity.class);
                startActivity(waitlistIntent);
            } else {
                // Initiate OAuth login for the selected college
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                GoogleSignInAccount account = task.getResult();
                if (account != null) {
                    validateAndAuthenticate(account);
                }
            } else {
                Toast.makeText(this, "Google sign-in failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateAndAuthenticate(GoogleSignInAccount account) {
        String userEmail = account.getEmail();

        // Check if the email domain matches the selected college domain
        if (userEmail != null && userEmail.endsWith("@" + collegeDomain)) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please use your college domain email to login.", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            googleSignInClient.signOut().addOnCompleteListener(task1 -> {});
        }
    }
}
