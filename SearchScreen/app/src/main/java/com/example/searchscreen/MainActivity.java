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
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.content.Intent;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1002;


    Camera camera;
    FrameLayout frameLayout;
    ImageView mImageView;
    ShowCamera showCamera;
    public static Button mCaptureBtn;
    public static Button mFromPhotos;
    public static Button showFunctions;
    public static Button openLiveOCR;
    Uri image_uri;

    // activity_find
    ConstraintLayout activity_find;
    ImageView imageView;
    GraphicOverlay graphicOverlay;
    TextView textView;
    Spinner spinner;
    Button button;

    // activity_search
    ImageView imageView2;
    SearchView searchView;
    EditText editText;
    EditText tempSearch;
    Button button2;

    public static ImageButton tutorialButton, backToCameraButton; // settingsButton
    public static Button translateButton, findButton, textToSpeechButton, googleButton, pictureButton, copyButton, searchButton, newPictureButton;
//    Button showFunctionsButtonOCR, newPictureButtonOCR;

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
        backToCameraButton.setVisibility(View.INVISIBLE);
        searchButton.setVisibility(View.INVISIBLE);

    }

    public static void functionButtonsOn() {

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
        searchButton.setVisibility(View.VISIBLE);

    } // end functionButtonsOn

    public static void  pictureButtonsOff(){

        mCaptureBtn.setVisibility(View.INVISIBLE);
        mFromPhotos.setVisibility(View.INVISIBLE);
        openLiveOCR.setVisibility(View.INVISIBLE);

    } // end pictureButtonsOff()

    public void pictureButtonsOn(){

        mCaptureBtn.setVisibility(View.VISIBLE);
        mFromPhotos.setVisibility(View.VISIBLE);
        openLiveOCR.setVisibility(View.VISIBLE);

    } // end pictureButtonsOn()

    public void findButtonsOff(){

    //    imageView.setVisibility(View.INVISIBLE);
        //graphicOverlay.setVisibility(View.INVISIBLE);
//        textView.setVisibility(View.INVISIBLE);
//        spinner.setVisibility(View.INVISIBLE);
//        button.setVisibility(View.INVISIBLE);

    } // end findButtonsOff()

    public void findButtonsOn(){

      //  imageView.setVisibility(View.VISIBLE);
        //graphicOverlay.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);

    } // end findButtonsOn()

    public void searchButtonsOff(){

        /*
        imageView2.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        tempSearch.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
*/
    } // end searchButtonsOff()

    public void searchButtonsOn() {
/*
        imageView2.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        tempSearch.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
*/
    } // end searchButtonsOn()

    public void otherFunctionsOff(){

        searchButtonsOff();
        findButtonsOff();

    } // end otherFunctionsOff()

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


    public void onSearchClick(View v)
    {
//        try {
//            Intent in = new Intent(Intent.ACTION_WEB_SEARCH);
//            String term = editTextInput.getText().toString();
//            in.putExtra(SearchManager.QUERY, term);
//            startActivity(in);
//        } catch (Exception e) {
//            // TODO: handle exception
    }

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
        tutorialButton = findViewById(R.id.tool_tip);
        backToCameraButton = findViewById(R.id.back_to_camera);
        translateButton = findViewById(R.id.translate_button);
        findButton = findViewById(R.id.find);
        textToSpeechButton = findViewById(R.id.text_to_speech);
        googleButton = findViewById(R.id.google);
        pictureButton = findViewById(R.id.picture);
        copyButton = findViewById(R.id.copy);
        newPictureButton = findViewById(R.id.new_picture);
        showFunctions = findViewById(R.id.show_functions);
        searchButton = findViewById(R.id.search);



        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mFromPhotos = findViewById(R.id.from_photos);
        openLiveOCR = findViewById(R.id.live_ocr);
//        showFunctionsButtonOCR = findViewById(R.id.show_functions_OCR);
//        newPictureButtonOCR = findViewById(R.id.new_picture_OCR);

        //  editTextInput =  findViewById(R.id.editTextInput);
        //  editTextInput.addTextChangedListener(editWatcher);
        //  settingsButton = findViewById(R.id.app_settings);
        //  graphicOverlay = findViewById(R.id.graphicOverlay);
        //  activity_find = findViewById(R.id.activity_find);
        //  this function will open OCR screen when live OCR button is clicked


        // INITIALIZE SETTINGS - taken out, but not commented out yet

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


        // set initial screen
        functionButtonsOff();
        searchButtonsOff();
       // imageView.setVisibility(View.INVISIBLE);
//        textView.setVisibility(View.INVISIBLE);
//        spinner.setVisibility(View.INVISIBLE);
//        button.setVisibility(View.INVISIBLE);
        tutorialButton.setVisibility(View.VISIBLE);
        settingsView.setVisibility(View.INVISIBLE);
        showFunctions.setVisibility(View.INVISIBLE);

        // graphicOverlay.setVisibility(View.INVISIBLE);
        // settingsButton.setVisibility(View.VISIBLE);

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

        openLiveOCR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openOCRActivity();
            }

        }); // end openLiveOCR

//        showFunctionsButtonOCR.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//    //            openOCRActivity();
//            }
//
//        }); // end showFunctionsButtonsOCR
//
//        newPictureButtonOCR.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//        //        openOCRActivity();
//            }
//
//        }); // end newPictureButtonOCR


        showFunctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                functionButtonsOn();
                otherFunctionsOff();
                pictureButtonsOff();
                showFunctions.setVisibility(View.INVISIBLE);
            }
        }); // end backToCameraButton.setOnClickListener

        newPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                functionButtonsOff();
                searchButtonsOff();
                findButtonsOff();
                pictureButtonsOn();
                mImageView.setVisibility(View.VISIBLE);
                showFunctions.setVisibility(View.VISIBLE);

            }

        }); // end newPictureButton.setOnClickListener

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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionButtonsOff();
                searchButtonsOn();
                newPictureButton.setVisibility(View.VISIBLE);
                showFunctions.setVisibility(View.VISIBLE);
            }

        }); // end translateButton.setOnClickListener


        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        }); // end translateButton.setOnClickListener

        findButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                otherFunctionsOff();
                functionButtonsOff();
                showFunctions.setVisibility(View.VISIBLE);
                newPictureButton.setVisibility(View.VISIBLE);

//                activity_find.setVisibility(View.VISIBLE);
 //               imageView.setVisibility(View.VISIBLE);
//                graphicOverlay.setVisibility(View.VISIBLE);
//                textView.setVisibility(View.VISIBLE);
//                spinner.setVisibility(View.VISIBLE);
//                button.setVisibility(View.VISIBLE);

//                FindActivity.wtf();



                Intent intent = new Intent(MainActivity.this, FindActivity.class);
                startActivity(intent);

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
//                pictureButtonsOff();
//                // mImageView.setVisibility(View.INVISIBLE);
//                // editTextInput.setVisibility(View.INVISIBLE);
//            functionButtonsOn();

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
//                mImageView.setVisibility(View.VISIBLE);
//                tutorialButton.setVisibility(View.VISIBLE);
//                newPictureButton.setVisibility(View.VISIBLE);
                functionButtonsOn();
                pictureButtonsOff();
            }});

    } // end onCreate

} // end MainActivity