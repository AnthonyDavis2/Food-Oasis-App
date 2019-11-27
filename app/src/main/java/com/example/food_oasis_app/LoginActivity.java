package com.example.food_oasis_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class LoginActivity extends AppCompatActivity {

    EditText userEmail, userPass;
    Button loginButton, registerButton;
    private FirebaseAuth mAuth;
    //ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ids are what we name our EditTexts in the xml
        userEmail = findViewById(R.id.userEmail);
        userPass = findViewById(R.id.userPass);
        //loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);


        //uses the firebase authentication object we created earlier
        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void loginUser() {

        //trim method eliminates leading and trailing spaces
        String email = userEmail.getText().toString().trim();
        String password = userPass.getText().toString().trim();

        if (email.isEmpty()) {
            userEmail.setError("Email field cannot be left blank");
            userEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Please enter a valid email address.");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            userPass.setError("Password is required!");
            userPass.requestFocus();
            return;
        }

        //loginProgressBar.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //loginProgressBar.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), VendorActivity.class));
                    //startActivity(new Intent(getApplicationContext(), SetProfile.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Login unsuccessful. Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}