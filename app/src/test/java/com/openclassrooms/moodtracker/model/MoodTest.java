package com.openclassrooms.moodtracker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

import org.junit.Test;

public class MoodTest extends TestCase {

    @Test
    public void testGetMoodStatus() {
        int moodStatus = 3;
        Mood mood = new Mood(moodStatus, "Feeling good");
        assertEquals(moodStatus, mood.getMoodStatus());
    }

    @Test
    public void testSetMoodStatus() {
        int moodStatus = 2;
        Mood mood = new Mood();
        mood.setMoodStatus(moodStatus);
        assertEquals(moodStatus, mood.getMoodStatus());
    }

    @Test
    public void testGetMoodComment() {
        String moodComment = "Feeling awesome!";
        Mood mood = new Mood(4, moodComment);
        assertEquals(moodComment, mood.getMoodComment());
    }

    @Test
    public void testSetMoodComment() {
        String moodComment = "Having a great day!";
        Mood mood = new Mood();
        mood.setMoodComment(moodComment);
        assertEquals(moodComment, mood.getMoodComment());
    }

    @Test
    public void testMoodConstructor() {
        int moodStatus = 1;
        String moodComment = "Feeling happy";
        Mood mood = new Mood(moodStatus, moodComment);
        assertEquals(moodStatus, mood.getMoodStatus());
        assertEquals(moodComment, mood.getMoodComment());
    }

    @Test
    public void testMoodSerialization() throws JsonProcessingException {
        int moodStatus = 2;
        String moodComment = "Feeling content";
        Mood mood = new Mood(moodStatus, moodComment);

        ObjectMapper mapper = new ObjectMapper();
        String moodJson = mapper.writeValueAsString(mood);

        Mood deserializedMood = mapper.readValue(moodJson, Mood.class);
        assertEquals(moodStatus, deserializedMood.getMoodStatus());
        assertEquals(moodComment, deserializedMood.getMoodComment());
    }

}