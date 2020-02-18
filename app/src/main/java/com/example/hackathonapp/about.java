package com.example.hackathonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    public void homeClick(MenuItem menuItem)
    {
        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(startIntent);
    }

    public void aboutClick(MenuItem menuItem)
    {
        Intent startIntent = new Intent(getApplicationContext(), about.class);
        startActivity(startIntent);
    }

    public void mapClick(MenuItem menuItem)
    {
        Intent startIntent = new Intent(getApplicationContext(), map.class);
        startActivity(startIntent);
    }


    public void reportClick(MenuItem menuItem)
    {
        Intent startIntent = new Intent(getApplicationContext(), report.class);
        startActivity(startIntent);
    }
}
