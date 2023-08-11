package com.openclassrooms.moodtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoodHistory {

    @JsonProperty("mood_list")
    Mood[] moodList = new Mood[7];

    public Mood[] getMoodList() {
        return moodList;
    }

    public void setMoodList(Mood[] moodList) {
        this.moodList = moodList;
    }


}
