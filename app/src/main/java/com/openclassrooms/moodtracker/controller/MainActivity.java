package com.openclassrooms.moodtracker.controller;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.openclassrooms.moodtracker.R;
import com.openclassrooms.moodtracker.model.ImageMap;
import com.openclassrooms.moodtracker.service.HistoryJob;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger(MainActivity.class.getName());

    public static final int HISTORY_ACTIVITY_REQUEST_CODE = 42;
    public static final String SHARED_USER_DATA = "SHARED_USER_DATA";
    public static final String SHARED_USER_MOOD = "SHARED_INDEX_COUNTER";
    public static final String SHARED_USER_MOOD_COMMENT = "SHARED_USER_MOOD_COMMENT";

    TextView mGreetingTextView;
    EditText mNameEditText;
    ImageButton mMoodButton;
    GestureDetector mGestureDetector;

    MediaPlayer mMediaPlayer;

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
        if(HISTORY_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            // fetch score from intent
            //int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageMap=new ImageMap();
        setContentView(R.layout.activity_main);

        mMoodButton = findViewById(R.id.main_button_happysmiley);
        mBackground = (LinearLayoutCompat) findViewById(R.id.main_background);
        mMainNoteaddButton = findViewById(R.id.main_button_noteadd);
        mMainHistoryButton = findViewById(R.id.main_button_history);

        mMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(SHARED_USER_DATA,MODE_PRIVATE)
                        .edit()
                        .putInt(SHARED_USER_MOOD,imageSequence.get(currentIndex))
                        .commit();
                playSound(imageSequence.get(currentIndex));
            }
        });



        mMainHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked History", Toast.LENGTH_LONG).show();
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(historyActivityIntent, HISTORY_ACTIVITY_REQUEST_CODE);

            }
        });


        //main_button_noteadd
        mMainNoteaddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View customDialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                //customDialogView
                EditText editText = customDialogView.findViewById(R.id.edit_text);

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

            }
        });
        mGestureDetector = new GestureDetector(this, new GestureListener());
        scheduleJob();
    }

    public void scheduleJob(){
        ComponentName componentName = new ComponentName(this, HistoryJob.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                //.setPeriodic(1 * 60 * 1000)
                .setMinimumLatency(60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS){
            logger.info("Job Successful");
        } else {
            logger.info("Job Failed");
        }
    }

    public void cancelJob(View v){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        logger.info("Job Cancelled");
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
            logger.info(e1.getX() + " " + e1.getY() + " " + e2.getX() + " " + e2.getY());
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void playSound(int moodNumber){
        String mood="";
        //final MediaPlayer mp =MediaPlayer.create(this, R.raw.);

        if (moodNumber==R.drawable.ic_smiley_super_happy){
            MediaPlayer.create(this,R.raw.superhappy).start();
            mood="Super Happy";
        } else if (moodNumber==R.drawable.ic_smiley_sad) {
            MediaPlayer.create(this,R.raw.sad).start();
            mood="Sad";
        } else if (moodNumber==R.drawable.ic_smiley_disappointed) {
            MediaPlayer.create(this,R.raw.disappointed).start();
            mood="Disappointed";
        } else if (moodNumber==R.drawable.ic_smiley_normal) {
            MediaPlayer.create(this,R.raw.normal).start();
            mood="Normal";
        } else if (moodNumber==R.drawable.ic_smiley_happy)  {
            MediaPlayer.create(this,R.raw.happy).start();
            mood="Happy";
        }
        Toast.makeText(MainActivity.this, mood, Toast.LENGTH_SHORT).show();
    }

}