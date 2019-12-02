package com.example.searchscreen;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.lang.Object;



public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{
    Camera camera;

    SurfaceHolder holder;

    public ShowCamera(Context context, Camera camera){
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);

    }


//    setFocusable(true);
//    setFocusableInTouchMode(true);

//    testing this thing

    // testing this thing again


    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)  {
            Log.d("down", "focusing now");


        }
        return true;
    }





    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

//        Camera.Parameters parameters = camera.getParameters();
//        parameters.setPreviewSize(previewSize.width, previewSize.height);
//        requestLayout();
//        camera.setParameters(parameters);
//
//
//        camera.startPreview();


        camera.stopPreview();
        Camera.Parameters p = camera.getParameters();
        p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        camera.setParameters(p);

        try
        {
            camera.setPreviewDisplay(holder);

        } catch (IOException ioe){
            ioe.printStackTrace();
        }


        camera.startPreview();
        camera.autoFocus(null);


        setFocusable(true);
        setFocusableInTouchMode(true);


    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {



        setFocusable(true);
        setFocusableInTouchMode(true);

        // get Camera parameters
        Camera.Parameters params = camera.getParameters();

//        List<String> focusModes = params.getSupportedFocusModes();
//        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
//            // Autofocus mode is supported
//        }




//        set the focus mode
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

//        set camera parameters
        camera.setParameters(params);




        // Create an instance of Camera
//        camera = getCameraInstance();

// set Camera parameters
//        Camera.Parameters params = camera.getParameters();

//        if (params.getMaxNumMeteringAreas() > 0){ // check that metering areas are supported
//            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
//
//
//            Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
//            meteringAreas.add(new Camera.Area(areaRect1, 600)); // set weight to 60%
//            Rect areaRect2 = new Rect(800, -1000, 1000, -800);  // specify an area in upper right of image
//            meteringAreas.add(new Camera.Area(areaRect2, 400)); // set weight to 40%
//            params.setMeteringAreas(meteringAreas);
//        }
//
//        camera.setParameters(params);






//        changed the orientation for the camera

        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size mSize = null;

        for(Camera.Size size : sizes){
            mSize = size;
        }



        if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            params.set("orientation","portrait");
            camera.setDisplayOrientation(90);
            params.setRotation(90);
        }
        else{
            params.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            params.setRotation(0);
        }

        params.setPictureSize(mSize.width, mSize.height);

        camera.setParameters(params);

        try{
            camera.setPreviewDisplay(holder);

            camera.startPreview();

        }catch (IOException e) {

            e.printStackTrace();
        }




    }

//    this function will take the picturef
//    @Override
//    public void onClick(View v) {
//        switch (previewState){
//            case K_STATE_FROZEN:
//                camera.startPreview();
//                previewState = K_STATE_PREVIEW;
//                break;
//
//            default:
//                camera.takePicture(null, rawCallback, null);
//                previewState = K_STATE_BUSY;
//        }// switch
//        shutterBtnConfig();
//    }


}
