package com.openclassrooms.moodtracker.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.moodtracker.model.Mood;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.openclassrooms.moodtracker.R;
import java.util.List;
import java.util.logging.Logger;

public class HistoryJobTest extends TestCase {

    Logger logger = Logger.getLogger(HistoryJob.class.getName());
    @Mock
    private ObjectMapper mapper;

    private HistoryJob mHistoryJob;

    public void testOnStartJob() {
    }

    public void testOnStopJob() {
    }

    public void testGetDefaultMoodList() {


        MockitoAnnotations.initMocks(this);

        try {

            mHistoryJob=new HistoryJob();

            List<Mood> expectedMoodList = List.of(
                new Mood(R.drawable.ic_smiley_happy, "Super Happy"),
                new Mood(R.drawable.ic_smiley_disappointed, "Hmm"),
                new Mood(R.drawable.ic_smiley_normal, ""),
                new Mood(0, ""),
                new Mood(R.drawable.ic_smiley_super_happy, "Wow again"),
                new Mood(R.drawable.ic_smiley_sad, ""),
                new Mood(R.drawable.ic_smiley_happy, "Happy Happy")
        );

            when(mapper.writeValueAsString(expectedMoodList)).thenReturn("Expected JSON String");

            String result = mHistoryJob.getDefaultMoodList();

            String expectedResult="[{\"mood_status\":2131165300,\"mood_comment\":\"Super Happy\"},{\"mood_status\":2131165299,\"mood_comment\":\"Hmm\"},{\"mood_status\":2131165301,\"mood_comment\":\"\"},{\"mood_status\":0,\"mood_comment\":\"\"},{\"mood_status\":2131165303,\"mood_comment\":\"Wow again\"},{\"mood_status\":2131165302,\"mood_comment\":\"\"},{\"mood_status\":2131165300,\"mood_comment\":\"Happy Happy\"}]";
            assertEquals(expectedResult, result);

        } catch (Exception e){
            logger.info("test failure");
        }
    }
}