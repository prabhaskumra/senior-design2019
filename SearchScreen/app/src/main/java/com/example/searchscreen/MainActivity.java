package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    Camera camera;

    FrameLayout frameLayout;
    ShowCamera showCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout2);



        final int MY_PERMISSIONS_REQUEST_CAMERA = 1;


//        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED){
//
//            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
//
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
//            }
//        } else {
//            // permissions already granted.
//            // open the camera
//            camera = Camera.open();
//        }

//         open the camera
        camera = Camera.open();



        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);


    }





}
