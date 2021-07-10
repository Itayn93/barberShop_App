package com.example.barbershop_app.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button registerButton;
    EditText registerFullName;
    EditText registerID;
    EditText registerEmailText;
    EditText registerPasswordText;
    EditText registerConfirmPassword;
    // int dbUserID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
        mDatabase = FirebaseDatabase.getInstance().getReference();// enable read/write to DB
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerButton = findViewById(R.id.buttonConfirmRegister);
        registerFullName = findViewById(R.id.editTextFullName);
        registerID = findViewById(R.id.editTextID);
        registerEmailText = findViewById(R.id.editTextEmailRegister);
        registerPasswordText = findViewById(R.id.editTextPasswordRegister);
        registerConfirmPassword = findViewById(R.id.editTextConfirmPassword);


    }

    @Override
    protected void onResume() {
        super.onResume();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = registerFullName.getText().toString();
                String id = registerID.getText().toString();
                String email = registerEmailText.getText().toString();
                String password = registerPasswordText.getText().toString();
                String confirmPassword = registerConfirmPassword.getText().toString();

                if (confirmPassword.equals(password)) {

                    // creating new User in firebase
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegisterActivity.this, "Signed in Successfully", Toast.LENGTH_LONG).show();
                                        //dbUserID++;
                                        writeNewUser(id,fullName,id,email,password);

                                        Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);// go to Main Menu
                                        startActivity(mainMenuIntent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Sign in Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }

                else {
                    Toast.makeText(RegisterActivity.this, "Password is not confirmed", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void writeNewUser(String userId, String fullName,String id, String email,String password) {
        User user = new User(fullName,id,email,password);

        mDatabase.child("users").child(userId).setValue(user);
    }
}