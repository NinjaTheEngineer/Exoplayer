package com.example.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.exoplayer.databinding.ActivityMainBinding;
import com.example.utility.UtilityClass;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;

public class MainActivity extends AppCompatActivity implements Player.Listener {

    private static String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private SimpleExoPlayer player;
    private UtilityClass utilityClass;

    private Uri videoUri = Uri.parse("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        initializeUtility();

        View view = binding.getRoot();
        setContentView(view);

        setUpExoPlayer();
    }

    //Setup ExoPlayer and it's Listeners
    private void setUpExoPlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);

        binding.btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartVideo(true);
            }
        });

        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        // Set the media item to be played.
        player.setMediaItem(mediaItem);
        // Prepare the player.
        player.prepare();
        // Start the playback.
        //player.play();
        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Log.d(TAG, "> VideoIsPlayerChanged = " + isPlaying);
                utilityClass.handleVideoIsPlayerChanged(isPlaying, player.getClock().currentTimeMillis());
                retrieveData();
            }

            @Override
            public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
                if(newPosition.contentPositionMs == 0 && reason == 1){
                    utilityClass.handledVideoRestarded();
                }
            }

            @Override
            public void onPlaybackStateChanged(int state) {
                if(utilityClass.checkIfVideoEnded(state)){
                    onVideoEnded();
                }
            }
        });
    }

    //Function to handle what visually happens when the video ends
    private void onVideoEnded(){
        binding.playerView.setVisibility(View.GONE);
        binding.viewPlaying.setVisibility(View.GONE);
        binding.viewVideoEnded.setVisibility(View.VISIBLE);
    }
    //Function to handle what visually happens when restarting the video after it ends
    private void restartVideo(Boolean resetData){
        player.seekToDefaultPosition();
        binding.playerView.setVisibility(View.VISIBLE);
        binding.viewPlaying.setVisibility(View.VISIBLE);
        binding.viewVideoEnded.setVisibility(View.GONE);

        utilityClass.resetData(resetData);
        retrieveData();
    }

    //Function to initialize UtilityClass
    private void initializeUtility() {
        utilityClass = new UtilityClass();
        retrieveData();
    }
    //Function to refresh data visualized
    private void retrieveData(){
        //Playing video layout
        binding.tvNumberOfPausedTimes.setText(utilityClass.getPausedTimes());
        binding.tvNumberOfResumedTimes.setText(utilityClass.getResumedTimes());
        binding.tvNumberOfRestartedTimes.setText(utilityClass.getRestartedTimes());
        binding.tvTimeElapsed.setText(utilityClass.getTimeElapsedBetweenPauses());
        //End video layout
        binding.tvNumberOfPausedTimesEnd.setText(utilityClass.getPausedTimes());
        binding.tvNumberOfResumedTimesEnd.setText(utilityClass.getResumedTimes());
        binding.tvNumberOfRestartedTimesEnd.setText(utilityClass.getRestartedTimes());
        binding.tvTimeElapsedEnd.setText(utilityClass.getTimeElapsedBetweenPauses());
        binding.tvTotalTimePaused.setText(utilityClass.getTotalTimePaused());
    }

}