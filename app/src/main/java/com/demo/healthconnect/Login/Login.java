package com.demo.healthconnect.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.healthconnect.Admin.AdminPanel;
import com.demo.healthconnect.Dashboard.Dashboard;
import com.demo.healthconnect.R;
import com.demo.healthconnect.Register.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText userName,password;
    Button login;
    TextView register,forgotPassword;
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication
    ProgressDialog loadingBar;

    String adminEmail = "admin@gmail.com";
    String adminPass = "admin123";
    public ProgressDialog progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dr_info);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login_btn);
        register = (TextView) findViewById(R.id.registerLink);
        forgotPassword = (TextView) findViewById(R.id.ForgetPassword);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        currentUser = mAuth.getCurrentUser();

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog


        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();
            }
        });

        //goto register page
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegister();
            }
        });

        //if user forgets the password then to reset it
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordUser();
            }
        });
    }

    private void resetPasswordUser() {
        String email = userName.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(Login.this,"Please enter your email id",Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Reset Email sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void sendUserToRegister() {
        //When user wants to create a new account send user to Register Activity
//        finish();
        Intent registerIntent = new Intent(Login.this, Register.class);
        startActivity(registerIntent);
    }

    private void AllowUserToLogin() {
        String email = userName.getText().toString().trim();
        String pwd = password.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(Login.this,"Please enter email id",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(Login.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else
        {
//            progress.show();
            //When both email and password are available log in to the account
            //Show the progress on Progress Dialog
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait ,Because Good things always take time");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())//If account login successful print message and send user to main Activity
                            {
                                loadingBar.dismiss();
                                if (email.equalsIgnoreCase(adminEmail))
                                    sendToAdminActivity();
                                else
//                                    progress.dismiss();
                                    sendToMainActivity();

                                Toast.makeText(Login.this,"Welcome to HealthConnect",Toast.LENGTH_SHORT).show();

                            }
                            else//Print the error message incase of failure
                            {
//                                progress.dismiss();
                                String msg = task.getException().toString();
                                Toast.makeText(Login.this,"Something Went Wrong, Please try again.",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    protected void onStart() {
        //Check if user has already signed in if yes send to mainActivity
        //This to avoid signing in everytime you open the app.
        super.onStart();
        if(currentUser!=null)
        {
            if (currentUser.getEmail().equalsIgnoreCase(adminEmail))
                sendToAdminActivity();
            else
                sendToMainActivity();
        }
    }

    private void sendToMainActivity() {
        //This is to send user to MainActivity
        finish();
        Intent  MainIntent = new Intent(Login.this, Dashboard.class);
        startActivity(MainIntent);
    }

    private void sendToAdminActivity() {
        //This is to send user to MainActivity
        finish();
        Intent  MainIntent = new Intent(Login.this, AdminPanel.class);
        startActivity(MainIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        finish();
        finishAffinity();
    }
}
