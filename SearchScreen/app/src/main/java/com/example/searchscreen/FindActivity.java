package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipboardManager;


import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.example.searchscreen.GraphicOverlay.Graphic;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;


public class FindActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "Main Activity"; //we use this for later inputs to keep string constant
    //FirebaseApp.initializeApp(this);

    //declare our buttons and assets and stufffz
    public EditText mEditText;                   //this is the text variable
    public static ImageView mImageView;
    private Button mSearchButton;
    private Bitmap mSelectedImage;              //use this variable to look over images and stuff
    private GraphicOverlay mGraphicOverlay;     //graphic overlay object used to overlay on top of the images found
    private TextView mTextView;               //textview object
    private Integer mImageMaxWidth;             //variable for max width of the image
    private Integer mImageMaxHeight;            //vairable for max height of the image
    private ImageView mBackButtonFind;
    private Button mCopyButton;                 //variable for the copy funcitonality
    private Button mSpeakButton;                //variable for the speak button
    private TextToSpeech tts;

    // buttons for user interface
    private Button mNewPictureButton, mGoogleButton;

    // buttons for Google activity
    private TextView textViewInput;

    /**
     * Name of the model file hosted with Firebase.
     */
    private static final String HOSTED_MODEL_NAME = "cloud_model_1";
    private static final String LOCAL_MODEL_ASSET = "mobilenet_v1_1.0_224_quant.tflite";
    /**
     * Dimensions of inputs.
     */
    private static final int DIM_BATCH_SIZE = 1;
    private static final int DIM_PIXEL_SIZE = 3;
    private static final int DIM_IMG_SIZE_X = 224;
    private static final int DIM_IMG_SIZE_Y = 224;
    /* Preallocated buffers for storing image data. */
    private final int[] intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];

    public Uri example;
    public Bitmap someImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        example = MainActivity.selectedImage;
        someImage = MainActivity.example;
        mImageView = findViewById(R.id.imageView);
        mImageView.setImageURI(MainActivity.selectedImage);
        mSearchButton = findViewById(R.id.button);
        mGraphicOverlay = findViewById(R.id.graphicOverlay);
        mEditText = findViewById(R.id.textSearch);
        mCopyButton = findViewById(R.id.copy_button);
        mSpeakButton = findViewById(R.id.text_to_speech_button);
//        mTextView = findViewById(R.id.textView);
        mNewPictureButton = findViewById(R.id.new_picture);
        mGoogleButton = findViewById(R.id.google_button);

        // hiding the Action bar AKA menu bar
//        getActionBar().hide();

        getSupportActionBar().hide();

        mNewPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        /** TEXT TO SPEECH AREA **/
        // Set up the Text tp speech engine
        // TODO: Set up the Text To Speech engine.
        TextToSpeech.OnInitListener listener =
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(final int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            Log.d("TTS", "Text to speech engine started successfully.");
                            tts.setLanguage(Locale.US);
                        } else {
                            Log.d("TTS", "Error starting the text to speech engine.");
                        }
                    }
                };
        tts = new TextToSpeech(this.getApplicationContext(), listener);

        /** BUTTONS **/
        //search button press to run text recognition
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTextRecognition();
            }
        });

        // copy button press for the text recognition
        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTextRecognitionCopy();
            }
        });
        mSpeakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tts.isSpeaking())
                {
                    tts.stop();
                }
                else{
                    runTextRecognitionSpeak();
                }

            }
        });
        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runTextRecognitionGoogle();


                } catch (Exception e) {
                    // TODO: handle exception
                }            }
        });


        //-----Ideally wanna take this spinner out, but right now if you take it out, itll break the code
        // because the bit bap is dependent on the adapter
        // need to figure out a way to take out the spinner
        Spinner dropdown = findViewById(R.id.spinner);
        dropdown.setVisibility(View.INVISIBLE);
        String[] items = new String[]{"Test Image 1 (Text)", "Test Image 2 (Text)", "Test Image 3" +
                " (Text)", "Test Image 4 (Text)", "Text Image 5 (Text)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

    } // end onCreate

    /**--------------------------------- TEXT RECOGNITION AREA ----------------------------------------------------**/

    /** SECTION FOR THE SPEAK TEXT RECOGNITION **/
    private void runTextRecognitionGoogle() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        mGoogleButton.setEnabled(false);
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                mGoogleButton.setEnabled(true);
                                processTextRecognitionResultGoogle(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                mGoogleButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    } // end runTextRecognitionSpeak

    private void processTextRecognitionResultGoogle(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();  //this clears the overlay of the graphics
        String sentence = "";  //added variable for the sentence

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    //Here is where we find the words that have finished post processing and we just have to concatenate everthing into a strin
                    sentence = sentence + elements.get(k).getText() + " ";  //sentence variable now has the recognized text in here
                }
            }
        }
        Log.i(TAG, sentence);  //this will log the current sentence to check if the sentence is being recognized correctly.
        Intent in = new Intent(Intent.ACTION_WEB_SEARCH);
        in.putExtra(SearchManager.QUERY, sentence);
        startActivity(in);

    } // end processTextRecognitionResultsSpeak

    private void runTextRecognitionSpeak() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        mSpeakButton.setEnabled(false);
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                mSpeakButton.setEnabled(true);
                                processTextRecognitionResultSpeak(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                mSpeakButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    } // end runTextRecognitionSpeak

    private void processTextRecognitionResultSpeak(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();  //this clears the overlay of the graphics
        String sentence = "";  //added variable for the sentence

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    //Here is where we find the words that have finished post processing and we just have to concatenate everthing into a strin
                    sentence = sentence + elements.get(k).getText() + " ";  //sentence variable now has the recognized text in here
                }
            }
        }
        Log.i(TAG, sentence);  //this will log the current sentence to check if the sentence is being recognized correctly.
        tts.speak(sentence, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
    } // end processTextRecognitionResultsSpeak

    /** SECTION FOR THE COPY TEXT RECOGNITION **/
    private void runTextRecognitionCopy() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        mCopyButton.setEnabled(false);
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                mCopyButton.setEnabled(true);
                                processTextRecognitionResultCopy(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                mCopyButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    } // end runTextRecognitionCopy

    private void processTextRecognitionResultCopy(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();  //this clears the overlay of the graphics
        String sentence = "";  //added variable for the sentence

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    //Here is where we find the words that have finished post processing and we just have to concatenate everthing into a strin
                    sentence = sentence + elements.get(k).getText() + " ";  //sentence variable now has the recognized text in here

                }
            }
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copiedText",sentence);
        clipboard.setPrimaryClip(clip);

        Log.i(TAG, sentence);  //this will log the current sentence to check if the sentence is being recognized correctly.

        showToast("Successfully copied to clipboard:" + sentence);
    } // end processTextRecognitionResultsCopy

    /** SECTION FOR THE SEARCH TEXT RECOGNITION **/

    private void runTextRecognition() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        mSearchButton.setEnabled(false);
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                mSearchButton.setEnabled(true);
                                processTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                mSearchButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();  //this clears the overlay of the graphics
        String sentence = "";  //added variable for the sentence

        String word =  mEditText.getText().toString(); //used to check if the word is present
        word = word.toLowerCase();                      //always check the lower case words;

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    //---- This code now here vvv will create a graphic text overlay that will and over each word that is found.
                    //Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
                    //mGraphicOverlay.add(textGraphic);

                    //this code implements the search function and overlays a red box if the word is found
                    String tempWord = elements.get(k).getText();
                    tempWord = tempWord.toLowerCase();
                    if(tempWord.equals(word))
                    {
                        Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
                        mGraphicOverlay.add(textGraphic);
                    }

                    //Here is where we find the words that have finished post processing and we just have to concatenate everthing into a strin
                    sentence = sentence + elements.get(k).getText() + " ";  //sentence variable now has the recognized text in here

                }
            }
        }

        Log.i(TAG, sentence);  //this will log the current sentence to check if the sentence is being recognized correctly.
        Log.i(TAG, word);
      //  mEditText.setText(sentence); //this will put the sentence into the text view

        if(word.equals(""))
        {
            showToast("No search text was provided");
        }
    }

    /**--------------------------------------------- END TEXT RECOGNITION AREA -----------------------------------------------------------------**/

    /**
     * Writes Image data into a {@code ByteBuffer}.
     */
    private synchronized ByteBuffer convertBitmapToByteBuffer(
            Bitmap bitmap, int width, int height) {
        ByteBuffer imgData =
                ByteBuffer.allocateDirect(
                        DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        imgData.order(ByteOrder.nativeOrder());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y,
                true);
        imgData.rewind();
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight());
        // Convert the image to int points.
        int pixel = 0;
        for (int i = 0; i < DIM_IMG_SIZE_X; ++i) {
            for (int j = 0; j < DIM_IMG_SIZE_Y; ++j) {
                final int val = intValues[pixel++];
                imgData.put((byte) ((val >> 16) & 0xFF));
                imgData.put((byte) ((val >> 8) & 0xFF));
                imgData.put((byte) (val & 0xFF));
            }
        }
        return imgData;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Functions for loading images from app assets.

    // Returns max image width, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxWidth() {
        if (mImageMaxWidth == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxWidth = mImageView.getWidth();
        }
        return mImageMaxWidth;
    }

    // Returns max image height, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxHeight() {
        if (mImageMaxHeight == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxHeight = mImageView.getHeight();
        }
        return mImageMaxHeight;
    }

    // Gets the targeted width / height.
    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        mGraphicOverlay.clear();
        mSelectedImage = someImage;

        if (mSelectedImage != null) {
            // Get the dimensions of the View
            Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

            int targetWidth = targetedSize.first;
            int maxHeight = targetedSize.second;

            // Determine how much to scale down the image
            float scaleFactor =
                    Math.max(
                            (float) mSelectedImage.getWidth() / (float) targetWidth,
                            (float) mSelectedImage.getHeight() / (float) maxHeight);

            Bitmap resizedBitmap =
                    Bitmap.createScaledBitmap(
                            mSelectedImage,
                            (int) (mSelectedImage.getWidth() / scaleFactor),
                            (int) (mSelectedImage.getHeight() / scaleFactor),
                            true);
            mImageView.setImageBitmap(resizedBitmap);
            mSelectedImage = resizedBitmap;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
