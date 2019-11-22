package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Camera camera;
    ShowCamera showCamera;

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
        frameLayout = findViewById(R.id.frameLayout2);

        // open the camera
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

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
