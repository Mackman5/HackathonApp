package com.example.hackathonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button reportnewsBtn = findViewById(R.id.reportnewsBtn),
               reportmapBtn = findViewById(R.id.mapreportBtn);

        reportnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), reportnews.class);
                startActivity(startIntent);
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
