package com.openclassrooms.moodtracker.service;


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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.moodtracker.R;
import com.openclassrooms.moodtracker.model.ImageMap;
import com.openclassrooms.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;

public class StoreCurrentMood extends AppCompatActivity {

    public static final String SHARED_USER_DATA = "SHARED_USER_DATA";
    public static final String SHARED_USER_MOOD = "SHARED_INDEX_COUNTER";
    public static final String MOOD_HISTORY = "MOOD_HISTORY";
    public static final String MOOD_LIST = "MOOD_LIST";

    public static final String SHARED_USER_MOOD_COMMENT = "SHARED_USER_MOOD_COMMENT";

    ObjectMapper mapper = new ObjectMapper();
    List<Mood> moodList;


    public void updateCurrentMood(){
        int currentMood = getSharedPreferences(SHARED_USER_DATA, MODE_PRIVATE)
                .getInt(SHARED_USER_MOOD, 0);

        String currentMoodComment = getSharedPreferences(SHARED_USER_DATA, MODE_PRIVATE)
                .getString(SHARED_USER_MOOD_COMMENT, "");

//        getSharedPreferences(SHARED_USER_DATA,MODE_PRIVATE)
//                .edit()
//                .putInt(SHARED_USER_MOOD,imageSequence.get(currentIndex))
//                .commit();

        String moodHistoryJson = getSharedPreferences(MOOD_HISTORY, MODE_PRIVATE)
                .getString(MOOD_LIST, getDefaultMoodList());

        try {
            moodList = mapper.readValue(moodHistoryJson, new TypeReference<List<Mood>>() {
            });
        }
        catch (Exception e){
            System.out.println("Error in current mood update");
            e.printStackTrace();
        }

        moodList.set(0,moodList.get(1));
        moodList.set(1,moodList.get(2));
        moodList.set(2,moodList.get(3));
        moodList.set(3,moodList.get(4));
        moodList.set(4,moodList.get(5));
        moodList.set(5,moodList.get(6));
        moodList.set(6,new Mood(currentMood,currentMoodComment));

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

}
