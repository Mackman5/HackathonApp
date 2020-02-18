package com.example.hackathonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        String actionId = intent.getStringExtra("id");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(actionId);
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

                String desc = values.get(0);
                String title = values.get(5);
                String time = values.get(4);
                String image = values.get(1);

                System.out.println("IMAGE BOI: " + image);
                try
                {
                    byte[] myImage = Base64.getDecoder().decode(image);

                    Bitmap bmp= BitmapFactory.decodeByteArray(myImage,0,myImage.length);
                    if (bmp!=null)
                    {
                        ImageView myView = (ImageView)findViewById(R.id.imageViewMain);
                        myView.setImageBitmap(bmp);
                    }
                }
                catch (Exception e)
                {
                    Log.e("Create file error : ",e.getMessage());
                }

                TextView titleText = findViewById(R.id.titleText),
                        timeText = findViewById(R.id.timeText),
                        descText = findViewById(R.id.descText);

                titleText.setText(title);
                timeText.setText(time);
                descText.setText(desc);

            }


            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                // Failed to read value
            }
        });


    }
}
