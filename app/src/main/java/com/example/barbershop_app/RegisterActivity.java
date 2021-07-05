package com.example.barbershop_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button registerButton;
    EditText registerEmailText;
    EditText registerPasswordText;
    EditText registerConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerButton = findViewById(R.id.buttonConfirmRegister);
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
                                        Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_LONG).show();
                                        Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);// go to Main Menu
                                        startActivity(mainMenuIntent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
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
}