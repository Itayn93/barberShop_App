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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button confirmSignInButton;
    EditText signInEmailText;
    EditText signInPasswordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        confirmSignInButton = findViewById(R.id.buttonCofirmSignIn);
        signInEmailText = findViewById(R.id.editTextEmailSignIn);
        signInPasswordText = findViewById(R.id.editTextPasswordSignIn);
    }

    @Override
    protected void onResume() {
        super.onResume();

        confirmSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signInEmailText.getText().toString();
                String password = signInPasswordText.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignInActivity.this,"Successful",Toast.LENGTH_LONG).show();
                                    Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);// go to Main Menu
                                    startActivity(mainMenuIntent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this,"Failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

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


//public int validateInput(String email, String password) {
//        String emailRegex = "^[a-zA-Z0-9+&*-]+(?:\." +
//                "[a-zA-Z0-9+&-]+)@" +
//                "(?:[a-zA-Z0-9-]+\.)+[a-z" +
//                "A-Z]{2,7}$";
//        Pattern pat = Pattern.compile(emailRegex);
//        if (email.isEmpty() || password.isEmpty()) { //email empty or password
//            return 0;
//        } else if (pat.matcher(email).matches()) { //email ok
//            return 1;
//        }
//        return 2;