package com.example.hackathonapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    HashMap<String, String> markerMap = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        float zoomLevel = 11.0f; //This goes up to 21
        LatLng Aurora = new LatLng(44.0065, -79.4504);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Aurora, zoomLevel));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GmtNum");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // This method is called once with the initial value and again
                String value = dataSnapshot.getValue(String.class);
                Integer iterations = Integer.parseInt(value);
                for(Integer i = 0; i < iterations; i++)
                {
                    final Integer rer = i;
                    DatabaseReference mydRef = database.getReference(i.toString());
                    mydRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            LatLng temp = new LatLng(Double.parseDouble(values.get(2)), Double.parseDouble(values.get(3)));

                            BitmapDescriptor markIcon = null;
                            if(title.toLowerCase() == "pothole") {
                            markIcon = BitmapDescriptorFactory.fromResource(R.drawable.pothole_marker);
                                Marker tempMark = mMap.addMarker(new MarkerOptions()
                                        .position(temp)
                                        .title(title)
                                        .snippet(desc)
                                        .icon(markIcon));
                                String tempId = tempMark.getId();
                                markerMap.put(tempId, rer.toString());
                            }
                            else
                            {
                                Marker tempMark = mMap.addMarker(new MarkerOptions()
                                        .position(temp)
                                        .title(title)
                                        .snippet(desc)
                                        .icon(markIcon));
                                String tempId = tempMark.getId();
                                markerMap.put(tempId, rer.toString());
                            }





                        }


                        @Override
                        public void onCancelled(DatabaseError error) {
                            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                            // Failed to read value
                        }
                    });
                }

                }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDED");
                // Failed to read value
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            public void onInfoWindowClick(Marker marker) {
                String actionId = markerMap.get(marker.getId());


                Intent startIntent = new Intent(getApplicationContext(), info.class);
                startIntent.putExtra("id", actionId);
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
