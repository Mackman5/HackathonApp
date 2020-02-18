package com.example.hackathonapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_main, null);

        // Create a LayoutParams for TextView
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT); // Height of TextView



        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView2);


        // Create a LinearLayout element
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lp);
        //ll.setGravity(Gravity.CENTER_HORIZONTAL);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("news1");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
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
                System.out.println(findViewById(R.id.text3));
                System.out.println(findViewById(R.id.text4));
                // Add text
                if (findViewById(R.id.text1) == null || findViewById(R.id.text2) == null) {
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(lp);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setId(R.id.text1);
                    tv.setText(values.get(1));
                    ll.addView(tv);
                    TextView tv2 = new TextView(getApplicationContext());
                    tv2.setLayoutParams(lp);
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv2.setId(R.id.text2);
                    tv2.setText(values.get(0));
                    ll.addView(tv2);
                }
                else
                {
                    TextView tempText = (TextView) findViewById(R.id.text1);
                    tempText.setText(values.get(1));

                    TextView tempText2 = (TextView) findViewById(R.id.text2);
                    tempText2.setText(values.get(0));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                // Failed to read value
            }
        });


        myRef = database.getReference("news2");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
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
                // Add text
                if (findViewById(R.id.text3) == null || findViewById(R.id.text4) == null) {
                    System.out.println(findViewById(R.id.text3));
                    System.out.println(findViewById(R.id.text4));
                    TextView tv3 = new TextView(getApplicationContext());
                    tv3.setLayoutParams(lp);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                    tv3.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv3.setId(R.id.text3);
                    tv3.setText(values.get(1));
                    ll.addView(tv3);
                    TextView tv2 = new TextView(getApplicationContext());
                    tv2.setLayoutParams(lp);
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv2.setId(R.id.text4);
                    tv2.setText(values.get(0));
                    ll.addView(tv2);
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                }
                else
                {
                    TextView tempText = (TextView) findViewById(R.id.text3);
                    tempText.setText(values.get(1));

                    TextView tempText2 = (TextView) findViewById(R.id.text4);
                    tempText2.setText(values.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                // Failed to read value
            }
        });


        myRef = database.getReference("news3");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
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
                System.out.println(findViewById(R.id.text5));
                System.out.println(findViewById(R.id.text6));
                if (findViewById(R.id.text5) == null || findViewById(R.id.text6) == null) {
                    // Add text
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(lp);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setId(R.id.text5);
                    tv.setText(values.get(1));
                    ll.addView(tv);
                    TextView tv2 = new TextView(getApplicationContext());
                    tv2.setLayoutParams(lp);
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv2.setId(R.id.text6);
                    tv2.setText(values.get(0));
                    ll.addView(tv2);
                }
                else
                {
                    TextView tempText = (TextView) findViewById(R.id.text5);
                    tempText.setText(values.get(1));

                    TextView tempText2 = (TextView) findViewById(R.id.text6);
                    tempText2.setText(values.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                // Failed to read value
            }
        });


        // Add the LinearLayout element to the ScrollView
        sv.addView(ll);

        // Display the view
        setContentView(v);

        myRef = database.getReference("IMG");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);


                try
                {
                    byte[] myImage = new byte[0];
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        myImage = Base64.getDecoder().decode(value);
                    }

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
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Exception ", "ERROR");
            }
        });

        Button thingstodoBtn = findViewById(R.id.thingstodoBtn),
                aboutBtn = findViewById(R.id.aboutBtn),
                mapBtn = findViewById(R.id.mapBtn),
                reportBtn = findViewById(R.id.reportBtn),
                accountBtn = findViewById(R.id.accountBtn);

        thingstodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), thingstodo.class);
                startActivity(startIntent);
            }
        });
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), about.class);
                startActivity(startIntent);
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), map.class);
                startActivity(startIntent);
            }
        });
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), report.class);
                startActivity(startIntent);
            }
        });
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), account.class);
                startActivity(startIntent);
            }
        });
    }

    public static void write(String ref, String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ref);
        myRef.setValue(value);
    }
}
