package com.openclassrooms.moodtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.openclassrooms.moodtracker.R;

import java.util.Arrays;
import java.util.List;

public class MoodHistory {


    @JsonProperty("mood_list")
    Mood[] moodList = new Mood[7];

    public Mood[] getMoodList() {
        return moodList;
    }

    public void setMoodList(Mood[] moodList) {
        this.moodList = moodList;
    }



//    @Override
//    public String toString() {
//        String moodListCsv="";
//        for(int i=0;i<7;i++)
//            moodListCsv=moodListCsv+moodList[i]+",";
//        return moodListCsv.substring(0,moodListCsv.length()-1);
//    }


}
