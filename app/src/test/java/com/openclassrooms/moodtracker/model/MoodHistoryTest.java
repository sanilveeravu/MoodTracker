package com.openclassrooms.moodtracker.model;

import junit.framework.TestCase;

import org.junit.Test;

public class MoodHistoryTest extends TestCase {
    @Test
    public void testGetMoodList() {
        Mood[] moodArray = new Mood[7];
        MoodHistory moodHistory = new MoodHistory();
        for(int i=0;i<moodArray.length;i++){
            assertEquals(moodArray[i], moodHistory.getMoodList()[i]);
        }
    }

    @Test
    public void testSetMoodList() {
        Mood[] newMoodArray = new Mood[7];
        MoodHistory moodHistory = new MoodHistory();
        moodHistory.setMoodList(newMoodArray);
        for(int i=0;i<newMoodArray.length;i++){
            assertEquals(newMoodArray[i], moodHistory.getMoodList()[i]);
        }
    }

    @Test
    public void testDefaultConstructor() {
        MoodHistory moodHistory = new MoodHistory();
        Mood[] moodArray = new Mood[7];
        for(int i=0;i<moodArray.length;i++){
            assertEquals(moodArray[i], moodHistory.getMoodList()[i]);
        }
    }
}