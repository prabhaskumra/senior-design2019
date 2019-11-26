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
  //  private EditText editTextInput;

    Camera camera;
    FrameLayout frameLayout;
    ImageView mImageView;
    ShowCamera showCamera;
    Button mCaptureBtn;
    Button mFromPhotos;
    Uri image_uri;

    ImageButton tutorialButton, backToCameraButton; // settingsButton
    Button translateButton, findButton, textToSpeechButton, googleButton, pictureButton, copyButton, newPictureButton;
    boolean pictureChosen = false;

    // public static FrameLayout frameLayout;
    public static ConstraintLayout main_after_picture;

    // vars for settings menu
    public static RecyclerView settingsView, translateSettings, findSettings, text_to_speech_Settings;
    public ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public void functionButtonsOff(){

        backToCameraButton.setVisibility(View.INVISIBLE);
        translateButton.setVisibility(View.INVISIBLE);
        findButton.setVisibility(View.INVISIBLE);
        textToSpeechButton.setVisibility(View.INVISIBLE);
        googleButton.setVisibility(View.INVISIBLE);
        pictureButton.setVisibility(View.INVISIBLE);
        copyButton.setVisibility(View.INVISIBLE);
        newPictureButton.setVisibility(View.INVISIBLE);
      //  settingsButton.setVisibility(View.INVISIBLE);
        tutorialButton.setVisibility(View.INVISIBLE);
        backToCameraButton.setVisibility(View.INVISIBLE);

    }

    public void functionButtonsOn(){

        backToCameraButton.setVisibility(View.VISIBLE);
        translateButton.setVisibility(View.VISIBLE);
        findButton.setVisibility(View.VISIBLE);
        textToSpeechButton.setVisibility(View.VISIBLE);
        googleButton.setVisibility(View.VISIBLE);
        pictureButton.setVisibility(View.VISIBLE);
        copyButton.setVisibility(View.VISIBLE);
        newPictureButton.setVisibility(View.VISIBLE);
        //settingsButton.setVisibility(View.VISIBLE);
        tutorialButton.setVisibility(View.VISIBLE);
        backToCameraButton.setVisibility(View.VISIBLE);

    }

    public void pictureButtonsOff(){

        mCaptureBtn.setVisibility(View.INVISIBLE);
        mFromPhotos.setVisibility(View.INVISIBLE);
      //  editTextInput.setVisibility(View.INVISIBLE);

    }

    public void pictureButtonsOn(){

        mCaptureBtn.setVisibility(View.VISIBLE);
        mFromPhotos.setVisibility(View.VISIBLE);
    //    editTextInput.setVisibility(View.VISIBLE);

    }


    //Tutorial Button Set up
    public void openTutorial(View view) {

        Intent intent = new Intent(MainActivity.this, Tutorial.class);
        startActivity(intent);

    } // end open tutorial


    /*
    public void onSearchClick(View v) {
        try {
            Intent in = new Intent(Intent.ACTION_WEB_SEARCH);
            String term = editTextInput.getText().toString();
            in.putExtra(SearchManager.QUERY, term);
            startActivity(in);
        } catch (Exception e) {
            // TODO: handle exception
        }

    } // end onSearchClick
*/
    private final TextWatcher editWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }; // end editWatcher

    private void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // camera intent
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
                }
                break;
            case GALLERY_REQUEST_CODE:
                //  if(resultCode == RESULT_OK){
                //      mImageView.setImageURI(image_uri);
                //  }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex((filePathColumn[0]));
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                mImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                mImageView.setImageURI(selectedImage);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }

    } // end onActivityResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mFromPhotos = findViewById(R.id.from_photos);
   //     editTextInput =  findViewById(R.id.editTextInput);
      //  editTextInput.addTextChangedListener(editWatcher);
       // settingsButton = findViewById(R.id.app_settings);
        tutorialButton = findViewById(R.id.tool_tip);
        backToCameraButton = findViewById(R.id.back_to_camera);
        translateButton = findViewById(R.id.translate_button);
        findButton = findViewById(R.id.find);
        textToSpeechButton = findViewById(R.id.text_to_speech);
        googleButton = findViewById(R.id.google);
        pictureButton = findViewById(R.id.picture);
        copyButton = findViewById(R.id.copy);
        newPictureButton = findViewById(R.id.new_picture);

        /* INITIALIZE SETTINGS */
        mImageUrls.add("https://en.meming.world/images/en/6/6e/Surprised_Pikachu.jpg");
        mNames.add("Find");

        mImageUrls.add("https://en.meming.world/images/en/6/6e/Surprised_Pikachu.jpg");
        mNames.add("Text-to-Speech");

        mImageUrls.add("https://en.meming.world/images/en/6/6e/Surprised_Pikachu.jpg");
        mNames.add("Translate");

        settingsView = findViewById(R.id.settings_menu);
        SettingsViewAdapter adapter = new SettingsViewAdapter(mNames, mImageUrls, this);
        settingsView.setAdapter(adapter);
        settingsView.setLayoutManager(new LinearLayoutManager(this));

        functionButtonsOff();
        settingsView.setVisibility(View.INVISIBLE);
    //    settingsButton.setVisibility(View.VISIBLE);
        tutorialButton.setVisibility(View.VISIBLE);

      /*  settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pictureChosen){
                    functionButtonsOff();
                    pictureButtonsOff();

                }
                else {
                    functionButtonsOff();
                    pictureButtonsOff();
                }
                backToCameraButton.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.INVISIBLE);
                settingsView.setVisibility(View.VISIBLE);

            }
        }); // end settingsButton.setOnClickListener
*/

        backToCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                functionButtonsOn();
                pictureButtonsOn();
                backToCameraButton.setVisibility(View.INVISIBLE);
                mImageView.setVisibility(View.VISIBLE);
                settingsView.setVisibility(View.INVISIBLE);

            }
        }); // end backToCameraButton.setOnClickListener

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        }); // end translateButton.setOnClickListener

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); // end findButton.setOnClickListener

        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); // end textToSpeechButton.setOnClickListener

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  /*  try {
                        Intent in = new Intent(Intent.ACTION_WEB_SEARCH);
                        String term = editTextInput.getText().toString();
                        in.putExtra(SearchManager.QUERY, term);
                        startActivity(in);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
*/

            }
        }); // end googleButton.setOnClickListener

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setVisibility(View.VISIBLE);
            }

        }); // end copyButton.setOnClickListener

        newPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                functionButtonsOff();
                mCaptureBtn.setVisibility(View.VISIBLE);
                mFromPhotos.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);

            }

        }); // end copyButton.setOnClickListener

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
                mImageView.setVisibility(View.VISIBLE);
                mCaptureBtn.setVisibility(View.INVISIBLE);
                mFromPhotos.setVisibility(View.INVISIBLE);
             //   mImageView.setVisibility(View.INVISIBLE);
               // editTextInput.setVisibility(View.INVISIBLE);
                translateButton.setVisibility(View.VISIBLE);
                findButton.setVisibility(View.VISIBLE);
                textToSpeechButton.setVisibility(View.VISIBLE);
                googleButton.setVisibility(View.VISIBLE);
                pictureButton.setVisibility(View.VISIBLE);
                copyButton.setVisibility(View.VISIBLE);
                newPictureButton.setVisibility(View.VISIBLE);

                pictureChosen = true;

            }});

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
                mImageView.setVisibility(View.VISIBLE);

                mCaptureBtn.setVisibility(View.INVISIBLE);
                mFromPhotos.setVisibility(View.INVISIBLE);
         //       editTextInput.setVisibility(View.INVISIBLE);

                translateButton.setVisibility(View.VISIBLE);
                findButton.setVisibility(View.VISIBLE);
                textToSpeechButton.setVisibility(View.VISIBLE);
                googleButton.setVisibility(View.VISIBLE);
                pictureButton.setVisibility(View.VISIBLE);
                copyButton.setVisibility(View.VISIBLE);
                newPictureButton.setVisibility(View.VISIBLE);

                pictureChosen = true;

            }});

    } // end onCreate

} // end MainActivity