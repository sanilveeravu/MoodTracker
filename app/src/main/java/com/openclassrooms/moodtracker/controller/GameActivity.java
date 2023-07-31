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
import android.widget.FrameLayout;
import android.widget.ImageButton;
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

    FrameLayout mFrameOneWeekAgo;
    FrameLayout mFrameSixDaysAgo;
    FrameLayout mFrameFiveDaysAgo;
    FrameLayout mFrameFourDaysAgo;
    FrameLayout mFrameThreeDaysAgo;
    FrameLayout mFrameTwoDaysAgo;
    FrameLayout mFrameYesterday;

    FrameLayout mFrameButtonOneWeekAgo;
    FrameLayout mFrameButtonSixDaysAgo;
    FrameLayout mFrameButtonFiveDaysAgo;
    FrameLayout mFrameButtonFourDaysAgo;
    FrameLayout mFrameButtonThreeDaysAgo;
    FrameLayout mFrameButtonTwoDaysAgo;
    FrameLayout mFrameButtonYesterday;

    Button mGameButton1;


    ImageButton mone_week_ago_button_noteadd;
    ImageButton msix_days_ago_button_noteadd;
    ImageButton mfive_days_ago_button_noteadd;
    ImageButton mfour_days_ago_button_noteadd;
    ImageButton mthree_days_ago_button_noteadd;
    ImageButton mtwo_days_ago_button_noteadd;
    ImageButton myesterday_button_noteadd;


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

            mFrameOneWeekAgo = findViewById(R.id.one_week_ago_left);
            mFrameSixDaysAgo = findViewById(R.id.six_days_ago_left);
            mFrameFiveDaysAgo = findViewById(R.id.five_days_ago_left);
            mFrameFourDaysAgo = findViewById(R.id.four_days_ago_left);
            mFrameThreeDaysAgo = findViewById(R.id.three_days_ago_left);
            mFrameTwoDaysAgo = findViewById(R.id.two_days_ago_left);
            mFrameYesterday = findViewById(R.id.yesterday_left);

            mFrameButtonOneWeekAgo = findViewById(R.id.one_week_ago_right);
            mFrameButtonSixDaysAgo = findViewById(R.id.six_days_ago_right);
            mFrameButtonFiveDaysAgo = findViewById(R.id.five_days_ago_right);
            mFrameButtonFourDaysAgo = findViewById(R.id.four_days_ago_right);
            mFrameButtonThreeDaysAgo = findViewById(R.id.three_days_ago_right);
            mFrameButtonTwoDaysAgo = findViewById(R.id.two_days_ago_right);
            mFrameButtonYesterday = findViewById(R.id.yesterday_right);

            mone_week_ago_button_noteadd=findViewById(R.id.one_week_ago_button_noteadd);
            msix_days_ago_button_noteadd=findViewById(R.id.six_days_ago_button_noteadd);
            mfive_days_ago_button_noteadd=findViewById(R.id.five_days_ago_button_noteadd);
            mfour_days_ago_button_noteadd=findViewById(R.id.four_days_ago_button_noteadd);
            mthree_days_ago_button_noteadd=findViewById(R.id.three_days_ago_button_noteadd);
            mtwo_days_ago_button_noteadd=findViewById(R.id.two_days_ago_button_noteadd);
            myesterday_button_noteadd=findViewById(R.id.yesterday_button_noteadd);

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

            String mone_week_ago_button_noteadd_text=updateMood(mOneWeekAgo, 0,mFrameOneWeekAgo, mFrameButtonOneWeekAgo,mone_week_ago_button_noteadd);
            String msix_days_ago_button_noteadd_text=updateMood(mSixDaysAgo, 1,mFrameSixDaysAgo, mFrameButtonSixDaysAgo,msix_days_ago_button_noteadd);
            String mfive_days_ago_button_noteadd_text=updateMood(mFiveDaysAgo, 2, mFrameFiveDaysAgo, mFrameButtonFiveDaysAgo,mfive_days_ago_button_noteadd);
            String mfour_days_ago_button_noteadd_text=updateMood(mFourDaysAgo, 3, mFrameFourDaysAgo, mFrameButtonFourDaysAgo,mfour_days_ago_button_noteadd);
            String mthree_days_ago_button_noteadd_text=updateMood(mThreeDaysAgo, 4, mFrameThreeDaysAgo, mFrameButtonThreeDaysAgo,mthree_days_ago_button_noteadd);
            String mtwo_days_ago_button_noteadd_text=updateMood(mTwoDaysAgo, 5, mFrameTwoDaysAgo, mFrameButtonTwoDaysAgo,mtwo_days_ago_button_noteadd);
            String myesterday_button_noteadd_text=updateMood(mYesterday, 6, mFrameYesterday, mFrameButtonYesterday,myesterday_button_noteadd);

            mone_week_ago_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, mone_week_ago_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });

            msix_days_ago_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, msix_days_ago_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });

            mfive_days_ago_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, mfive_days_ago_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });

            mfour_days_ago_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, mfour_days_ago_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });

            mthree_days_ago_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, mthree_days_ago_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });

            mtwo_days_ago_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, mtwo_days_ago_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });

            myesterday_button_noteadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GameActivity.this, myesterday_button_noteadd_text, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            System.out.println("Error in History Activity");
            e.printStackTrace();
        }

    }

    String updateMood(TextView mDay,int mPosition, FrameLayout mFrame, FrameLayout mFrameButton, ImageButton mImageButton){
        Mood currentMood=moodList.get(mPosition);
        mDay.setBackgroundColor(getColor(mImageMap.getImageToColor().get(currentMood.getMoodStatus())));
        mFrameButton.setBackgroundColor(getColor(mImageMap.getImageToColor().get(currentMood.getMoodStatus())));
        ViewGroup.LayoutParams params=mDay.getLayoutParams();
        params.width=mImageMap.getImageToWidth().get(currentMood.getMoodStatus());

        System.out.println(currentMood.getMoodComment());
        if(currentMood.getMoodComment().isEmpty() || currentMood.getMoodComment().equals("")){
            mImageButton.setVisibility(View.INVISIBLE);
        }



        currentMood.getMoodComment();
        mDay.setLayoutParams(params);
        //mFrame.setLayoutParams(params);
        ViewGroup.LayoutParams frameparams=mFrame.getLayoutParams();
        frameparams.width=mImageMap.getImageToWidth().get(currentMood.getMoodStatus());

        mFrame.setLayoutParams(frameparams);
        return currentMood.getMoodComment();
    }

    public String getDefaultMoodList(){
        List<Mood> moodListDefault = new ArrayList();
        String moodHistoryDefault="";

        try {
            moodListDefault = new ArrayList();
            moodListDefault.add(new Mood(R.drawable.ic_smiley_happy, "Super Happy"));
            moodListDefault.add(new Mood(R.drawable.ic_smiley_disappointed, "Hmm"));
            moodListDefault.add(new Mood(R.drawable.ic_smiley_normal, ""));
            moodListDefault.add(new Mood(0, ""));
            moodListDefault.add(new Mood(R.drawable.ic_smiley_super_happy, "Wow again"));
            moodListDefault.add(new Mood(R.drawable.ic_smiley_sad, ""));
            moodListDefault.add(new Mood(R.drawable.ic_smiley_happy, "Happy Happy"));

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