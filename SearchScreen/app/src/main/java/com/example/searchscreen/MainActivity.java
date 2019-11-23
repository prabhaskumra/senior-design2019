package com.example.searchscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1002;
    private EditText editTextInput;

    Camera camera;
    FrameLayout frameLayout;
    ImageView mImageView;
    ShowCamera showCamera;
    Button mCaptureBtn;
    Button mFromPhotos;
    Uri image_uri;

    public static FrameLayout frameLayout;
    public static ConstraintLayout main_after_picture;

    // vars for settings menu
    public static RecyclerView settingsView, translateSettings, findSettings, text_to_speech_Settings;
    public static boolean settingsOpen = false;
    public ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mFromPhotos = findViewById(R.id.from_photos);
//        frameLayout = findViewById(R.id.frameLayout2);
        editTextInput =  findViewById(R.id.editTextInput);
        editTextInput.addTextChangedListener(editWatcher);

        final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

//        camera = Camera.open();


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

        initImageBitmaps();

        /* **************** SETTINGS STUFF************************ */
        final ImageButton settingsButton = findViewById(R.id.app_settings);
        final ImageButton functionButton = findViewById(R.id.function_menu);
        final ImageButton tutorialButton = findViewById(R.id.tool_tip);
        final ImageButton backToCameraButton = findViewById(R.id.back_to_camera);
        /* ******************************************************* */

        final Button translateButton = findViewById(R.id.translate_button);
        final Button findButton = findViewById(R.id.find);
        final Button textToSpeechButton = findViewById(R.id.text_to_speech);
        final Button googleButton = findViewById(R.id.google);
        final Button pictureButton = findViewById(R.id.picture);
        final Button copyButton = findViewById(R.id.copy);

        settingsView.setVisibility(View.INVISIBLE);

        backToCameraButton.setVisibility(View.INVISIBLE);
        translateButton.setVisibility(View.INVISIBLE);
        findButton.setVisibility(View.INVISIBLE);
        textToSpeechButton.setVisibility(View.INVISIBLE);
        googleButton.setVisibility(View.INVISIBLE);
        pictureButton.setVisibility(View.INVISIBLE);
        copyButton.setVisibility(View.INVISIBLE);


        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                frameLayout.setVisibility(View.INVISIBLE);
                settingsView.setVisibility(View.VISIBLE);
                functionButton.setVisibility(View.INVISIBLE);
                tutorialButton.setVisibility(View.INVISIBLE);
                settingsButton.setVisibility(View.INVISIBLE);
                backToCameraButton.setVisibility(View.VISIBLE);

                translateButton.setVisibility(View.VISIBLE);
                findButton.setVisibility(View.VISIBLE);
                textToSpeechButton.setVisibility(View.VISIBLE);
                googleButton.setVisibility(View.VISIBLE);
                pictureButton.setVisibility(View.VISIBLE);
                copyButton.setVisibility(View.VISIBLE);
            }
        });

        backToCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setVisibility(View.VISIBLE);
                settingsView.setVisibility(View.INVISIBLE);
                functionButton.setVisibility(View.VISIBLE);
                tutorialButton.setVisibility(View.VISIBLE);
                settingsButton.setVisibility(View.VISIBLE);
                backToCameraButton.setVisibility(View.INVISIBLE);

                translateButton.setVisibility(View.INVISIBLE);
                findButton.setVisibility(View.INVISIBLE);
                textToSpeechButton.setVisibility(View.INVISIBLE);
                googleButton.setVisibility(View.INVISIBLE);
                pictureButton.setVisibility(View.INVISIBLE);
                copyButton.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void initImageBitmaps(){
        mImageUrls.add("https://en.meming.world/images/en/6/6e/Surprised_Pikachu.jpg");
        mNames.add("Find");

        mImageUrls.add("https://en.meming.world/images/en/6/6e/Surprised_Pikachu.jpg");
        mNames.add("Text-to-Speech");

        mImageUrls.add("https://en.meming.world/images/en/6/6e/Surprised_Pikachu.jpg");
        mNames.add("Translate");

        initRecyclerView();
    }

    private void initRecyclerView(){
        settingsView = findViewById(R.id.settings_menu);
        SettingsViewAdapter adapter = new SettingsViewAdapter(mNames, mImageUrls, this);
        settingsView.setAdapter(adapter);
        settingsView.setLayoutManager(new LinearLayoutManager(this));
    }


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

//        showCamera = new ShowCamera(this, camera);
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
    private final TextWatcher editWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        };
    };




    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // camera intent
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
//                if(resultCode == RESULT_OK){
//                    mImageView.setImageURI(image_uri);
//                }
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
