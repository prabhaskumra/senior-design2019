package com.example.searchscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1002;

    public static Uri selectedImage;
    public static Bitmap example;

    ImageView mImageView;
    public static Button mCaptureBtn;
    public static Button mFromPhotos;
    public static Button showFunctions;
    public static Button openLiveOCR;
    public static Uri image_uri;

    public static ImageButton tutorialButton;



    public void openFindActivity() {
        Intent intent = new Intent(MainActivity.this, FindActivity.class);
        startActivity(intent);
    }


    //  this function will open OCR screen when live OCR button is clicked
    public void openOCRActivity(){

        Intent intent = new Intent(this, OcrCaptureActivity.class);
        startActivity(intent);

    } // end openOCRActivity


    //Tutorial Button Set up
    public void openTutorial(View view) {

        Intent intent = new Intent(MainActivity.this, Tutorial.class);
        startActivity(intent);

    } // end open tutorial


    /*
    private final TextWatcher editWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }; // end editWatcher
    */


    private void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
        mImageView.setVisibility(View.VISIBLE);

    } // end openCamera

    //    open photos from gallery
    private void pickFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
        mImageView.setVisibility(View.VISIBLE);

    } // end pickFromGallery

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                        // openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

    } // end onRequestPermissionResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_CAPTURE_CODE:
                if (resultCode == RESULT_OK) {
                  mImageView.setImageURI(image_uri);
                    BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                    example = drawable.getBitmap();

//                    mImageView.setImageURI(selectedImage);
                }
                break;
            case GALLERY_REQUEST_CODE:

                //  if(resultCode == RESULT_OK){
                //      mImageView.setImageURI(image_uri);
                //  }
                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex((filePathColumn[0]));
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
              //  mImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                mImageView.setImageURI(selectedImage);
                example =  BitmapFactory.decodeFile(imgDecodableString);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);

        } // end switch statement

        mImageView.setImageResource(R.drawable.homescreen);
        openFindActivity();

    } // end onActivityResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mFromPhotos = findViewById(R.id.from_photos);
        tutorialButton = findViewById(R.id.tool_tip);
        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mFromPhotos = findViewById(R.id.from_photos);
        openLiveOCR = findViewById(R.id.live_ocr);
        tutorialButton.setVisibility(View.VISIBLE);
        openLiveOCR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openOCRActivity();
            }

        }); // end openLiveOCR

        final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
        // camera = Camera.open();
        // function button to capture photos from camera. Also asks for permissions
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        // permission already granted
                        openCamera();
                    }
                } else {
                    openCamera();
                }

            }}); // end mCaptureBtn.setOnClickListener

        // function button to pick photos from camera gallery. Also asks for permissions
        mFromPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        // permission already granted
                        pickFromGallery();
                    }
                }
                else {
                    pickFromGallery();
                }

            }
        });

    } // end onCreate

} // end MainActivity