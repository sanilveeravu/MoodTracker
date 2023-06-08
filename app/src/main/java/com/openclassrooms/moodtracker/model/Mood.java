package com.openclassrooms.moodtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.moodtracker.R;

public class Mood {

    @JsonProperty("mood_status")
    int moodStatus;

    @JsonProperty("mood_comment")
    String moodComment;

    public int getMoodStatus() {
        return moodStatus;
    }

    public void setMoodStatus(int moodStatus) {
        this.moodStatus = moodStatus;
    }

    public String getMoodComment() {
        return moodComment;
    }

    public void setMoodComment(String moodComment) {
        this.moodComment = moodComment;
    }

    public Mood(int moodStatus, String moodComment) {
        this.moodStatus = moodStatus;
        this.moodComment = moodComment;
    }

    public Mood() {
    }

}
