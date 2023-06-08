package com.openclassrooms.moodtracker.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.openclassrooms.moodtracker.R;
import com.openclassrooms.moodtracker.model.ImageMap;
import com.openclassrooms.moodtracker.model.Mood;
import com.openclassrooms.moodtracker.model.MoodHistory;
import com.openclassrooms.moodtracker.model.Question;
import com.openclassrooms.moodtracker.model.QuestionBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String MOOD_HISTORY = "MOOD_HISTORY";
    public static final String MOOD_LIST = "MOOD_LIST";

    public static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    TextView mOneWeekAgo;
    TextView mSixDaysAgo;
    TextView mFiveDaysAgo;
    TextView mFourDaysAgo;
    TextView mThreeDaysAgo;
    TextView mTwoDaysAgo;
    TextView mYesterday;

    Button mGameButton1;

    MoodHistory mMoodHistory;

    ImageMap mImageMap;


    List<Mood> moodList;



    int mScore;
    private boolean mEnableTouchEvents;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(BUNDLE_STATE_SCORE,mScore);
        //outState.putInt(BUNDLE_STATE_QUESTION_COUNT,mRemainingQuestionCount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            mImageMap = new ImageMap();

            mEnableTouchEvents = true;
            mOneWeekAgo = findViewById(R.id.one_week_ago_text);
            mSixDaysAgo = findViewById(R.id.six_days_ago_text);
            mFiveDaysAgo = findViewById(R.id.five_days_ago_text);
            mFourDaysAgo = findViewById(R.id.four_days_ago_text);
            mThreeDaysAgo = findViewById(R.id.three_days_ago_text);
            mTwoDaysAgo = findViewById(R.id.two_days_ago_text);
            mYesterday = findViewById(R.id.yesterday_text);

            mMoodHistory = new MoodHistory();



//            getSharedPreferences(MOOD_HISTORY, MODE_PRIVATE)
//                    .edit()
//                    .putString(MOOD_LIST, moodHistoryDefault)
//                    .commit();

            String moodHistoryJson = getSharedPreferences(MOOD_HISTORY, MODE_PRIVATE)
                    .getString(MOOD_LIST, getDefaultMoodList());

            moodList = mapper.readValue(moodHistoryJson, new TypeReference<List<Mood>>() {});



            //moodList = mapper.readValue(moodHistoryJson, Mood[].class);

            //mMoodHistory.setMoodList(Arrays.stream(mMoodHistoryCsv.split(",")).mapToInt(Integer::parseInt).toArray());

            updateMood(mOneWeekAgo, 0);
            updateMood(mSixDaysAgo, 1);
            updateMood(mFiveDaysAgo, 2);
            updateMood(mFourDaysAgo, 3);
            updateMood(mThreeDaysAgo, 4);
            updateMood(mTwoDaysAgo, 5);
            updateMood(mYesterday, 6);
        }
        catch (Exception e){
            System.out.println("Error in History Activity");
            e.printStackTrace();
        }

    }

    void updateMood(TextView mDay,int mPosition){
        Mood currentMood=moodList.get(mPosition);
        mDay.setBackgroundColor(getColor(mImageMap.getImageToColor().get(currentMood.getMoodStatus())));
        ViewGroup.LayoutParams params=mDay.getLayoutParams();
        params.width=mImageMap.getImageToWidth().get(currentMood.getMoodStatus());
        mDay.setLayoutParams(params);
    }

    public String getDefaultMoodList(){
        List<Mood> moodListDefault = new ArrayList();
        String moodHistoryDefault="";

        try {
            moodListDefault = new ArrayList();
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(0, ""));

            moodHistoryDefault = mapper.writeValueAsString(moodListDefault);
        }
        catch (Exception e) {
           System.out.println("Error in Defaulting") ;
            e.printStackTrace();
        }
        return moodHistoryDefault;

    }

    @Override
    public void onClick(View v) {
        int index;
//        if (v == mGameButton1) {
//            index = 0;
//        } else if (v == mGameButton2) {
//            index = 1;
//        } else if (v == mGameButton3) {
//            index = 2;
//        } else if (v == mGameButton4) {
//            index = 3;
//        } else {
//            throw new IllegalStateException("Unknown View " + v);
//        }
//
//        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()){
//            mScore++;
//            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
//        }
//        mEnableTouchEvents = false;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                endGame();
//                mEnableTouchEvents = true;
//            }
//        },2000);
    }

}