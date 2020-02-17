package com.example.hackathonapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reportnews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportnews);

        Button submitnewsBtn = findViewById(R.id.submitnewsBtn);

        final EditText headlineInput = findViewById(R.id.headlineInput),
                 announcementInput = findViewById(R.id.announcementInput);

        submitnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String headline = headlineInput.getText().toString(),
                       announcement = announcementInput.getText().toString();

                write("news/headline",headline);
                write("news/announcement",announcement);
            }
        });
    }
    //FIREBASE WRITE
    public static void write(String ref, String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ref);
        myRef.setValue(value);
    }
}
