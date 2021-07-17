package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop_app.classes.JsonIO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dbUsers;
    Button confirmSignInButton;
    EditText signInEmailText;
    EditText signInPasswordText;
    User signedInUser = new User();
    //User user = new User();
    String userObj;
    int signInSuccessful = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Log.d("Lifecycle: ", "LoggedInActivity onCreate SignInActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        dbUsers = FirebaseDatabase.getInstance().getReference("users");




    }

    @Override
    protected void onStart() {
        super.onStart();
      //  Log.d("Lifecycle: ", "LoggedInActivity onStart SignInActivity");
        confirmSignInButton = findViewById(R.id.buttonCofirmSignIn);
        signInEmailText = findViewById(R.id.editTextEmailSignIn);
        signInPasswordText = findViewById(R.id.editTextPasswordSignIn);

    }

    @Override
    protected void onResume() {
        super.onResume();
       // Log.d("Lifecycle: ", "LoggedInActivity onResume SignInActivity");

        confirmSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Lifecycle: ", "LoggedInActivity onClick SignInActivity");

                String email = signInEmailText.getText().toString();
                String password = signInPasswordText.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignInActivity.this,"Sign In Successful",Toast.LENGTH_LONG).show();

                                    signInSuccessful = 1;

                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this,"No Such User In DB",Toast.LENGTH_LONG).show();
                                }

                                if (signInSuccessful == 1){ //// get current signed in user and put extra to main menu activity using JsonIO class
                                    dbUsers.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            //User signedInUser = new User();
                                            //Log.d("Lifecycle: ", "LoggedInActivity onDataChange SignInActivity");
                                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                                signedInUser = dsp.getValue(User.class);
                                                if (signedInUser.getEmail().equals(email))
                                                    break;
                                            }
                                            try {
                                                // Log.d("Lifecycle: ", "LoggedInActivity INSIDE TRY SignInActivity");
                                                userObj = JsonIO.Object_to_JsonString(signedInUser);
                                            } catch (JsonProcessingException e) {
                                                //Log.d("Lifecycle: ", "LoggedInActivity INSIDE CATCH SignInActivity");
                                                e.printStackTrace();
                                            }

                                            if (email.contains("@admin")){
                                                Intent adminMenuIntent = new Intent(getApplicationContext(), AdminMenuActivity.class);// go to Admin Menu
                                                startActivity(adminMenuIntent);
                                            }

                                            else {
                                                Intent userMenuIntent = new Intent(getApplicationContext(), UserMenuActivity.class);// go to User Menu

                                                userMenuIntent.putExtra("userObj", userObj);
                                                //Log.d("Lifecycle: ", "LoggedInActivity putExtra SignInActivity");
                                                startActivity(userMenuIntent);
                                                //Log.d("Lifecycle: ", "LoggedInActivity AFTER startActivity SignInActivity");
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                           // Log.d("Lifecycle: ", "LoggedInActivity onCancelled SignInActivity");
                                        }
                                    });

                                }

                            }

                        });

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle: ", "LoggedInActivity onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle: ", "LoggedInActivity onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle: ", "LoggedInActivity onDestroy");

    }

}


