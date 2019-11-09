package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Camera camera;

    FrameLayout frameLayout;
    ShowCamera showCamera;

    // vars for settings menu
    public static RecyclerView settingsView, translateSettings, findSettings, text_to_speech_Settings;
    public boolean settingsOpen = false;
    public ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout2);

        // open the camera

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

        // stuff for settings menu
        initImageBitmaps();
        settingsView.setVisibility(View.INVISIBLE);
        ImageButton settingsButton = findViewById(R.id.app_settings);
        settingsButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (!settingsOpen){
                settingsOpen = true;
                frameLayout.setVisibility(View.INVISIBLE);
                settingsView.setVisibility(View.VISIBLE);
            }
            else{
                settingsOpen = false;
                frameLayout.setVisibility(View.VISIBLE);
                settingsView.setVisibility(View.INVISIBLE);
            }
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
