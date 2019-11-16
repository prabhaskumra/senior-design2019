package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    Camera camera;

    FrameLayout frameLayout;
    ShowCamera showCamera;

    private EditText editTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout2);
        editTextInput =  findViewById(R.id.editTextInput);

        // open the camera

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);




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
