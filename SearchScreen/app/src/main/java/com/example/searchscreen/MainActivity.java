package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.app.SearchManager;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Camera camera;

    FrameLayout frameLayout;
    ImageView mImageView;
    ShowCamera showCamera;

    private EditText editTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        frameLayout = findViewById(R.id.frameLayout2);
        mImageView = findViewById(R.id.image_view);
        editTextInput =  findViewById(R.id.editTextInput);


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
//        frameLayout.addView(showCamera);




    }
    //Tutorial Button Set up
    public void openTutorial(View view)
    {
        Intent intent = new Intent(MainActivity.this, Tutorial.class );
        startActivity(intent);
    }

    public void onSearchClick(View v)
    {
        try {
            Intent in = new Intent(Intent.ACTION_WEB_SEARCH);
            String term = editTextInput.getText().toString();
            in.putExtra(SearchManager.QUERY, term);
            startActivity(in);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


}
