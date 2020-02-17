package com.example.hackathonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class report extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mAuth = FirebaseAuth.getInstance();

        Button reportnewsBtn = findViewById(R.id.reportnewsBtn),
               reportmapBtn = findViewById(R.id.mapreportBtn);

        reportnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null)
                {
                    Intent startIntent = new Intent(getApplicationContext(), reportnews.class);
                    startActivity(startIntent);
                }
                else
                {
                    Intent startIntent = new Intent(getApplicationContext(), login.class);
                    startActivity(startIntent);
                }
            }
        });
        reportmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), reportmap.class);
                startActivity(startIntent);
            }
        });
    }

}
