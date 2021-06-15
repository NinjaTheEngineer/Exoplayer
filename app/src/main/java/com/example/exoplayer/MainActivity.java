package com.example.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.exoplayer.databinding.ActivityMainBinding;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Uri videoUri = Uri.parse("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpExoPlayer();
    }

    private void setUpExoPlayer() {
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(getApplicationContext()).build();
        binding.playerView.setPlayer(player);

        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        // Set the media item to be played.
        player.setMediaItem(mediaItem);
        // Prepare the player.
        player.prepare();
        // Start the playback.
        player.play();
    }
}