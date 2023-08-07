package com.openclassrooms.moodtracker.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.moodtracker.R;
import com.openclassrooms.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.List;

public class HistoryJob extends JobService {

    public static final String MOOD_HISTORY = "MOOD_HISTORY";
    public static final String MOOD_LIST = "MOOD_LIST";

    public static final String SHARED_USER_DATA = "SHARED_USER_DATA";
    public static final String SHARED_USER_MOOD = "SHARED_INDEX_COUNTER";
    public static final String SHARED_USER_MOOD_COMMENT = "SHARED_USER_MOOD_COMMENT";


    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean onStartJob(JobParameters params) {
        try {
            System.out.println("test job");
            String moodHistoryJson = getSharedPreferences(MOOD_HISTORY, MODE_PRIVATE)
                    .getString(MOOD_LIST, getDefaultMoodList());

            System.out.println(moodHistoryJson);

            int mMood = getSharedPreferences(SHARED_USER_DATA, MODE_PRIVATE)
                    .getInt(SHARED_USER_MOOD, 0);

            String mMoodComment = getSharedPreferences(SHARED_USER_DATA, MODE_PRIVATE)
                    .getString(SHARED_USER_MOOD_COMMENT, "");

            List<Mood> moodList = mapper.readValue(moodHistoryJson, new TypeReference<List<Mood>>() {});

            System.out.print("m0");
            moodList.get(0).getMoodComment();

            Mood m1=moodList.get(1);
            System.out.print("m1");
            System.out.println(m1.getMoodComment());
            System.out.print("m2");
            Mood m2=moodList.get(2);
            System.out.println(m2.getMoodComment());
            System.out.print("m3");
            Mood m3=moodList.get(3);
            System.out.println(m3.getMoodComment());
            System.out.print("m4");
            Mood m4=moodList.get(4);
            System.out.println(m4.getMoodComment());
            System.out.print("m5");
            Mood m5=moodList.get(5);
            System.out.println(m5.getMoodComment());
            System.out.print("m6");
            Mood m6=moodList.get(6);
            System.out.println(m6.getMoodComment());

            moodList.clear();
            moodList.add(0,m1);
            moodList.add(1,m2);
            moodList.add(2,m3);
            moodList.add(3,m4);
            moodList.add(4,m5);
            moodList.add(5,m6);

//            moodList.add(0,moodList.get(1));
//            moodList.add(1,moodList.get(2));
//            moodList.add(2,moodList.get(3));
//            moodList.add(3,moodList.get(4));
//            moodList.add(4,moodList.get(5));
//            moodList.add(5,moodList.get(6));

            Mood currentMood=new Mood(mMood, mMoodComment);

            moodList.add(6,currentMood);

            System.out.print("m7");
            System.out.println(currentMood.getMoodComment());

            System.out.println("done test job");

            getSharedPreferences(MOOD_HISTORY,MODE_PRIVATE)
                    .edit()
                    .putString(MOOD_LIST,mapper.writeValueAsString(moodList))
                    .commit();

            System.out.println(mapper.writeValueAsString(moodList));

        } catch (Exception e){
            System.out.println("Error In Job");
        }


        return false;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
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
}
