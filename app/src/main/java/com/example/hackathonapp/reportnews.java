package com.example.hackathonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

                if (headline == "" || announcement == "")
                {
                    Toast toast =Toast.makeText(getApplicationContext(), "Please enter valid Headline and Announcement", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("news2");

                    // Read from the database
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            List<String> keys = new ArrayList<>();
                            for(DataSnapshot keyNode: dataSnapshot.getChildren())
                            {
                                keys.add(keyNode.getKey());
                            }
                            List<String> values  = new ArrayList<>();
                            for(Integer i = 0; i < keys.size(); i++)
                            {
                                values.add(dataSnapshot.child(keys.get(i)).getValue().toString());
                            }
                            write("news3/headline",values.get(1));
                            write("news3/announcement",values.get(0));
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                            // Failed to read value
                        }
                    });


                    myRef = database.getReference("news1");

                    // Read from the database
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            List<String> keys = new ArrayList<>();
                            for(DataSnapshot keyNode: dataSnapshot.getChildren())
                            {
                                keys.add(keyNode.getKey());
                            }
                            List<String> values  = new ArrayList<>();
                            for(Integer i = 0; i < keys.size(); i++)
                            {
                                values.add(dataSnapshot.child(keys.get(i)).getValue().toString());
                            }
                            write("news2/headline",values.get(1));
                            write("news2/announcement",values.get(0));
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                            // Failed to read value
                        }
                    });
                    try
                    {
                        TimeUnit.SECONDS.sleep(3);
                        write("news1/headline", headline);
                        write("news1/announcement",announcement);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
    }
    //FIREBASE WRITE
    public static void write(String ref, String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ref);
        myRef.setValue(value);
    }

    }

