package com.example.utility;

import java.text.DecimalFormat;

public class UtilityClass {

    private int pausedTimes = 0;
    private int resumedTimes = 0;
    private int restartedTimes = 0;

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

    private long pausedTimeMillis = 0;

    DecimalFormat df = new DecimalFormat("#.##");

    private double timePaused = 0f;

    public void handleVideoIsPlayerChanged(boolean isPlaying, long currentTimeMillis){
        if(isPlaying){
            resumedTimes += 1;
            timePaused = pausedTimeMillis != 0 ? ((currentTimeMillis - pausedTimeMillis) * 0.001) : 0;
        }else{
            pausedTimes += 1;
            pausedTimeMillis = currentTimeMillis;
        }
    }

    public void handledVideoRestarded() {
        restartedTimes += 1;
    }

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

    public boolean checkIfVideoEnded(int state) {
        if(state == STATE_ENDED){
            return true;
        }else{
            return false;
        }
    }
}