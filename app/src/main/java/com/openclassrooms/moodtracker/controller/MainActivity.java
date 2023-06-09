package com.openclassrooms.moodtracker.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.openclassrooms.moodtracker.R;
import com.openclassrooms.moodtracker.model.ImageMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String SHARED_USER_DATA = "SHARED_USER_DATA";
    public static final String SHARED_USER_MOOD = "SHARED_INDEX_COUNTER";
    public static final String SHARED_USER_MOOD_COMMENT = "SHARED_USER_MOOD_COMMENT";

    TextView mGreetingTextView;
    EditText mNameEditText;
    ImageButton mMoodButton;
    GestureDetector mGestureDetector;

    LinearLayoutCompat mBackground;

    ImageButton mMainNoteaddButton;

    ImageButton mMainHistoryButton;

    int currentIndex=0;
    int maxIndex=5;

    ImageMap mImageMap;

    private static List<Integer> imageSequence = Arrays.asList(
            R.drawable.ic_smiley_happy,
            R.drawable.ic_smiley_super_happy,
            R.drawable.ic_smiley_sad,
            R.drawable.ic_smiley_disappointed,
            R.drawable.ic_smiley_normal);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            // fetch score from intent
            //int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageMap=new ImageMap();
        setContentView(R.layout.activity_main);

//        String firstName=getSharedPreferences(SHARED_PREF_USER_INFO,MODE_PRIVATE)
//                .getString(SHARED_PERF_USER_INFO_NAME,null);

//        mGreetingTextView = findViewById(R.id.main_textview_greeting);
//        mNameEditText = findViewById(R.id.main_edittext_greeting);
        mMoodButton = findViewById(R.id.main_button_happysmiley);
        mBackground = (LinearLayoutCompat) findViewById(R.id.main_background);
        mMainNoteaddButton = findViewById(R.id.main_button_noteadd);
        mMainHistoryButton = findViewById(R.id.main_button_history);

//        mPlayButton.setEnabled(false);
//        mNameEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                mPlayButton.setEnabled(!s.toString().isEmpty());
//            }
//        });

        mMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(SHARED_USER_DATA,MODE_PRIVATE)
                        .edit()
                        .putInt(SHARED_USER_MOOD,imageSequence.get(currentIndex))
                        .commit();
                Toast.makeText(MainActivity.this, "You clicked Happy", Toast.LENGTH_LONG).show();

            }
        });



        mMainHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked History", Toast.LENGTH_LONG).show();
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

            }
        });


        //main_button_noteadd
        mMainNoteaddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View customDialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

                //final EditText input = new EditText(MainActivity.this);

                //customDialogView
                EditText editText = customDialogView.findViewById(R.id.edit_text);

                //input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(MainActivity.this)
                        //.setTitle("Comment")
                        //.setMessage("Comment")
                        //.setView(input)
                        .setView(customDialogView)
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_LONG).show();
                                getSharedPreferences(SHARED_USER_DATA,MODE_PRIVATE)
                                        .edit()
                                        .putString(SHARED_USER_MOOD_COMMENT,editText.getText().toString())
                                        .commit();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_LONG).show();
                //Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
//                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

            }
        });
        mGestureDetector = new GestureDetector(this, new GestureListener());
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getY()>e1.getY()+50) {
                Toast.makeText(MainActivity.this, "Down Fling", Toast.LENGTH_SHORT).show();
                if(currentIndex==0)
                    currentIndex=4;
                else
                    currentIndex--;
                mMoodButton.setImageResource(imageSequence.get(currentIndex));
                mBackground.setBackgroundColor(getColor(mImageMap.getImageToColor().get(imageSequence.get(currentIndex))));
            } else if (e2.getY()<e1.getY()+50){
                Toast.makeText(MainActivity.this, "Up Fling", Toast.LENGTH_SHORT).show();
                if(currentIndex==4)
                    currentIndex=0;
                else
                    currentIndex++;
                mMoodButton.setImageResource(imageSequence.get(currentIndex));
                mBackground.setBackgroundColor(getColor(mImageMap.getImageToColor().get(imageSequence.get(currentIndex))));
            }
            System.out.println(e1.getX() + " " + e1.getY() + " " + e2.getX() + " " + e2.getY());
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}