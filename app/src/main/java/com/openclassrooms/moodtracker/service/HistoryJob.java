package com.openclassrooms.moodtracker.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.moodtracker.R;
import com.openclassrooms.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HistoryJob extends JobService {

    public static final String MOOD_HISTORY = "MOOD_HISTORY";
    public static final String MOOD_LIST = "MOOD_LIST";

    public static final String SHARED_USER_DATA = "SHARED_USER_DATA";
    public static final String SHARED_USER_MOOD = "SHARED_INDEX_COUNTER";
    public static final String SHARED_USER_MOOD_COMMENT = "SHARED_USER_MOOD_COMMENT";


    ObjectMapper mapper = new ObjectMapper();

    Logger logger = Logger.getLogger(HistoryJob.class.getName());


    @Override
    public boolean onStartJob(JobParameters params) {
        try {
            logger.info("Schedule job started");
            String moodHistoryJson = getSharedPreferences(MOOD_HISTORY, MODE_PRIVATE)
                    .getString(MOOD_LIST, getDefaultMoodList());

            int mMood = getSharedPreferences(SHARED_USER_DATA, MODE_PRIVATE)
                    .getInt(SHARED_USER_MOOD, 0);

            String mMoodComment = getSharedPreferences(SHARED_USER_DATA, MODE_PRIVATE)
                    .getString(SHARED_USER_MOOD_COMMENT, "");

            List<Mood> moodList = mapper.readValue(moodHistoryJson, new TypeReference<List<Mood>>() {});

            moodList.get(0).getMoodComment();
            Mood m1=moodList.get(1);
            Mood m2=moodList.get(2);
            Mood m3=moodList.get(3);
            Mood m4=moodList.get(4);
            Mood m5=moodList.get(5);
            Mood m6=moodList.get(6);

            moodList.clear();
            moodList.add(0,m1);
            moodList.add(1,m2);
            moodList.add(2,m3);
            moodList.add(3,m4);
            moodList.add(4,m5);
            moodList.add(5,m6);

            Mood currentMood=new Mood(mMood, mMoodComment);

            moodList.add(6,currentMood);

            logger.info("done Scheduled job");

            getSharedPreferences(MOOD_HISTORY,MODE_PRIVATE)
                    .edit()
                    .putString(MOOD_LIST,mapper.writeValueAsString(moodList))
                    .commit();

            getSharedPreferences(SHARED_USER_DATA,MODE_PRIVATE)
                    .edit()
                    .putString(SHARED_USER_MOOD_COMMENT,"")
                    .commit();

            getSharedPreferences(SHARED_USER_DATA,MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_USER_MOOD,0)
                    .commit();


        } catch (Exception e){
            logger.info("Error In Job");
        }

        ComponentName componentName = new ComponentName(this, HistoryJob.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                //.setPeriodic(1 * 60 * 1000)
                .setMinimumLatency(60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS){
            logger.info("Job Successful");
        } else {
            logger.info("Job Failed");
        }

        jobFinished(params,false);


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
            logger.info("Error in Defaulting") ;
            e.printStackTrace();
        }
        return moodHistoryDefault;

    }
}
