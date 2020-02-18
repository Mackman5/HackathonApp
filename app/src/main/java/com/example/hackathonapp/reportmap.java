package com.example.hackathonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import java.util.Calendar;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.hackathonapp.MainActivity.write;

public class reportmap extends AppCompatActivity {

    String pathToFile;
    String name;
    Bitmap bitmap;
    double lat;
    double lng;
    boolean takenPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportmap);
        //get user location
        GPSTracker g = new GPSTracker(getApplicationContext());
        Location l = g.getLocation();
        lat = -1;
        lng = -1;
        if(l != null){
            lat = l.getLatitude();
            lng = l.getLongitude();
        }
        //get date and time

        //upload a picture

        //BUTTON
        Button takePicture = (Button) findViewById(R.id.takePicture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get and display picture
                dispatchCamera();
                takenPicture = true;

            }
        });

        //BUTTON
        Button submit = (Button) findViewById(R.id.submitReport);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText typeText = (EditText) findViewById(R.id.reportType);
                final EditText descText = (EditText) findViewById(R.id.reportDesc);
                ImageView camView = (ImageView) findViewById(R.id.pictureScreen);
                if (typeText.getText().toString().equals("") || typeText.getText() == null || descText.getText().toString().equals("") || descText.getText() == null || camView == null || takenPicture == false)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Please enter valid values",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //write values to firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("GmtNum");

                    // Read from the database
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String value = dataSnapshot.getValue(String.class);
                            Log.d("Data", "Value is: " + value);
                            int val = Integer.parseInt(value);
                            //update marker counter
                            write("GmtNum", Integer.toString(val+1));

                            //write rest of data
                            write(value + "/lat", Double.toString(lat));
                            write(value + "/lng", Double.toString(lng));

                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            write(value + "/time", currentDate + " " + currentTime);

                            File f =  new File(pathToFile);
                            String encodstring = encodeFileToBase64Binary(f);
                            write(value + "/img", encodstring);


                            String type = typeText.getText().toString();
                            write(value + "/type", type);


                            String desc = descText.getText().toString();
                            write(value + "/desc", desc);



                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("Data", "Failed to read value.", error.toException());
                        }
                    });

                    Intent startIntent = new Intent(getApplicationContext(), map.class);
                    startActivity(startIntent);
                }

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                bitmap = BitmapFactory.decodeFile(pathToFile);
                ImageView camView = (ImageView) findViewById(R.id.pictureScreen);
                camView.setImageBitmap(bitmap);



            }

        }
    }

    private void dispatchCamera(){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager()) != null){
            File photoFile = createPhotoFile();
            if(photoFile!=null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.hackathonapp.fileprovider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePic, 1);

            }
        }
    }

    private File createPhotoFile(){
        name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e){
            Log.d("log", "Excep : " + e.toString());
        }
        return image;
    }

    private static String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

}
