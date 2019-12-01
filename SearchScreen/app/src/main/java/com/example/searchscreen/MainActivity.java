package com.example.searchscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1002;


    Camera camera;
    FrameLayout frameLayout;
    ImageView mImageView;
    ShowCamera showCamera;
    Button mCaptureBtn;
    Button mFromPhotos;
    Button openLiveOCR;
    Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mFromPhotos = findViewById(R.id.from_photos);
        openLiveOCR = findViewById(R.id.live_ocr);

        final int MY_PERMISSIONS_REQUEST_CAMERA = 1;


        //  this function will open OCR screen when live OCR button is clicked
        openLiveOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOCRActivity();
            }
        });


//        function button to capture photos from camera. Also asks for permissions
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
//                         permission already granted
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }

            }
        });


//        function button to pick photos from camera gallery. Also asks for permissions
        mFromPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
//                         permission already granted
                        pickFromGallery();
                    }
                }
                else {
                    pickFromGallery();
                }
            }
        });
    }


//  this function will open OCR screen when live OCR button is clicked
    public void openOCRActivity(){
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        startActivity(intent);
    }


    //Tutorial Button Set up
    public void openTutorial(View view)
    {
        Intent intent = new Intent(MainActivity.this, Tutorial.class );
        startActivity(intent);
    }

    public void onSearchClick(View v)
    {
//        try {
//            Intent in = new Intent(Intent.ACTION_WEB_SEARCH);
//            String term = editTextInput.getText().toString();
//            in.putExtra(SearchManager.QUERY, term);
//            startActivity(in);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }

    }


    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    //    open photos from gallery
    private void pickFromGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED  ) {
//                    openCamera();
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_CAPTURE_CODE:
                if (resultCode == RESULT_OK) {
                    mImageView.setImageURI(image_uri);
                }

                break;
            case GALLERY_REQUEST_CODE:

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex((filePathColumn[0]));
                String imgDecodableString = cursor.getString(columnIndex);

                cursor.close();
                mImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
}
