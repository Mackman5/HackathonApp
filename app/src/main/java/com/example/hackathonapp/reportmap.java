package com.example.hackathonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.database.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.hackathonapp.MainActivity.write;

public class reportmap extends AppCompatActivity {

    String pathToFile;
    String name;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportmap);
        //get user location
        GPSTracker g = new GPSTracker(getApplicationContext());
        Location l = g.getLocation();
        double lat = -1;
        double lng = -1;
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
                dispatchCamera();
                //gets a picture
            }
        });

        //submit values
        write("lat", Double.toString(lat));
        write("lng", Double.toString(lng));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                bitmap = BitmapFactory.decodeFile(pathToFile);
                ImageView camView = (ImageView) findViewById(R.id.pictureScreen);
                camView.setImageBitmap(bitmap);


                File f =  new File(pathToFile);
                String encodstring = encodeFileToBase64Binary(f);
                write("IMG", encodstring);
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
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
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
