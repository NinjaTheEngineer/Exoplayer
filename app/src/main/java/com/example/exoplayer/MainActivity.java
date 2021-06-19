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

    private void setUpExoPlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);

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
                refreshData();
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
                    Log.d(TAG, "> onPlaybackStateChanged Video ENDED");
                }
            }
        });
    }

    private void initializeUtility() {
        utilityClass = new UtilityClass();
        refreshData();
    }

    private void refreshData(){
        binding.tvNumberOfPausedTimes.setText(utilityClass.getPausedTimes());
        binding.tvNumberOfResumedTimes.setText(utilityClass.getResumedTimes());
        binding.tvNumberOfRestartedTimes.setText(utilityClass.getRestartedTimes());
        binding.tvTimeElapsed.setText(utilityClass.getTimeElapsedBetweenPauses());
    }

}