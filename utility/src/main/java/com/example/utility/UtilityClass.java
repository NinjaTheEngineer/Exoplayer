package com.example.utility;

import java.text.DecimalFormat;

public class UtilityClass {
    //Variables
    private int pausedTimes = 0;
    private int resumedTimes = 0;
    private int restartedTimes = 0;
    private long pausedTimeMillis = 0;
    private double timePaused = 0f;
    private double totalTimePaused = 0f;

     // The player does not have any media to play.
    private int STATE_IDLE = 1;
     //The player is not able to immediately play from its current position. This state typically
     //occurs when more data needs to be loaded.
    private int STATE_BUFFERING = 2;
     // The player is able to immediately play from its current position. The player will be playing if
     // getPlayWhenReady() is true, and paused otherwise.
    private int STATE_READY = 3;
     // The player has finished playing the media.
    private int STATE_ENDED = 4;

    DecimalFormat df = new DecimalFormat("#.##");

    //Count how many times the video is resumed and paused, and calculates the amount of time elapsed between each pause/resume
    public void handleVideoIsPlayerChanged(boolean isPlaying, long currentTimeMillis){
        if(isPlaying){
            resumedTimes += 1;
            timePaused = pausedTimeMillis != 0 ? ((currentTimeMillis - pausedTimeMillis) * 0.001) : 0;
            totalTimePaused += timePaused;
        }else{
            pausedTimes += 1;
            pausedTimeMillis = currentTimeMillis;
        }
    }
    //Increments restarted times, should be called when video restarts
    public void handledVideoRestarded() {
        restartedTimes += 1;
    }
    //Function to check if the video playing has ended
    public boolean checkIfVideoEnded(int state) {
        if(state == STATE_ENDED){
            return true;
        }else{
            return false;
        }
    }
    //Functions below return all data in the correct visualization format
    public String getPausedTimes(){
        return "Pauses = "+ pausedTimes;
    }
    public String getResumedTimes(){
        return "Resumed = " + resumedTimes;
    }
    public String getRestartedTimes(){
        return "Restarted = " + restartedTimes;
    }
    public String getTimeElapsedBetweenPauses(){
        return "Time paused = " + df.format(timePaused) + "s";
    }
    public String getTotalTimePaused() {
        return "Total time paused = " + df.format(totalTimePaused) + "s";
    }

    //Reset data values
    public void resetData(Boolean resetData) {
        if(resetData){
            pausedTimes = 0;
            resumedTimes = 0;
            restartedTimes = 0;
        }
        
        timePaused = 0;
        pausedTimeMillis = 0;
        totalTimePaused = 0;
    }
}