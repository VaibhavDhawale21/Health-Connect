package com.demo.healthconnect.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.healthconnect.Login.Login;
import com.demo.healthconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    Button logout;
    FirebaseAuth mAuth;//Used for firebase authentication
    FirebaseUser currentUser;//used to store current user of account
    TextView username, password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        logout = findViewById(R.id.logout);
        username = (TextView) findViewById(R.id.userNameProfile);
        password = (TextView) findViewById(R.id.passwordProfile);

        username.setText(currentUser.getEmail());
//        password.setText(currentUser.);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sendToLoginActivity();
            }
        });
    }

    private void sendToLoginActivity() {
        finish();
        Intent MainIntent = new Intent(Profile.this, Login.class);
        startActivity(MainIntent);
    }

}