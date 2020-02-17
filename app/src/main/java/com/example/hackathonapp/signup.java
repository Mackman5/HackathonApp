package com.example.hackathonapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String redirect_destination = "com.example.hackathonapp.reportnews";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        final EditText emailInput = findViewById(R.id.emailInput),
                 passwordInput = findViewById(R.id.passwordInput),
                 confirmPasswordInput = findViewById(R.id.confirmPasswordInput)  ;
        Button signupBtn = findViewById(R.id.signupBtn),
               changepageBtn = findViewById(R.id.changepageBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString(),
                       password = passwordInput.getText().toString(),
                       confirmPassword = confirmPasswordInput.getText().toString();

                if (!(email.contains("@")))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(password.equals(confirmPassword))
                {
                    createAccount(email,password);
                }
                else if(password.length() < 6)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Password should be at least 6 characters long", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        changepageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), login.class);
                startActivity(startIntent);
            }
        });





    }


    public void redirect(String REDIRECT_CLASS)
    {
        try {
            Class<?> c = Class.forName(REDIRECT_CLASS);
            Intent startIntent = new Intent(getApplicationContext(),c);
            startActivity(startIntent);
        }
        catch (Exception e)
        {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + e);
        }
    }

    public void createAccount(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        redirect(redirect_destination);
    }
}
