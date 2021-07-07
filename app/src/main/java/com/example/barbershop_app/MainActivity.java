package com.example.barbershop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.barbershop_app.activities.RegisterActivity;
import com.example.barbershop_app.activities.SignInActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button signInButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle: ", "LoggedInActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth


    }

    @Override
    protected void onStart() {
        Log.d("Lifecycle: ", "LoggedInActivity onStart");
        super.onStart();

        signInButton = findViewById(R.id.buttonSignIn);
        registerButton = findViewById(R.id.buttonRegister);

    }

    @Override
    protected void onResume() {
        Log.d("Lifecycle: ", "LoggedInActivity onResume");
        super.onResume();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToSignInPage(v);
                Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToRegisterPage(v);
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);

            }
        });
    }

    @Override
    protected void onPause() {
        Log.d("Lifecycle: ", "LoggedInActivity onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("Lifecycle: ", "LoggedInActivity onResume");
        super.onDestroy();
    }





    public void getDataFromFirebase(){


    }



}