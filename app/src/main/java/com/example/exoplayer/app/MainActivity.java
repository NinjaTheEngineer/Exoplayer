package com.example.exoplayer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.data.model.DefaultResponse;
import com.example.exoplayer.data.network.ApiClient;
import com.example.exoplayer.data.network.ApiInterface;
import com.example.exoplayer.databinding.ActivityMainBinding;
import com.example.exoplayer.domain.features.video_events.SendShowFirstFrameUseCase;
import com.example.exoplayer.domain.features.video_events.SendVideoFinishedUseCase;
import com.example.exoplayer.domain.features.video_events.SendVideoStartLoadUseCase;
import com.example.utility.UtilityClass;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements Player.Listener {

    private static String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private SimpleExoPlayer player;
    private UtilityClass utilityClass;

    private boolean videoFirstLoaded = false;

    private ApiInterface apiClient;

    private Uri videoUri = Uri.parse("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        apiClient = ApiClient.createApiInterface();
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

            @Override
            public void onRenderedFirstFrame() {
                notifyFirstFrameShown();
            }

            @Override
            public void onIsLoadingChanged(boolean isLoading) {
                if(isLoading && !videoFirstLoaded){
                    notifyVideoStartLoad();
                    videoFirstLoaded = true;
                }
            }
        });
    }

    //Function to handle what visually happens when the video ends
    private void onVideoEnded(){
        notifyVideoFinished();
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

    //Functions to call API
    private void notifyFirstFrameShown(){
        //sendShowFirstFrameUseCase.execute(new DefaultRequest("Video Start Load"), new SendShowFirstFrameUseCaseSubscriber());
        Call<DefaultResponse> call = apiClient.sendShowFirstFrame(new DefaultRequest("Video Start Load"));

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d(TAG, ">sendShowFirstFrame call - " + call + " _ -> response - " + response);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d(TAG, ">sendShowFirstFrame call - " + call + " _ -> t - " + t.getLocalizedMessage());

            }
        });
    }
    private void notifyVideoStartLoad(){
        //sendVideoStartLoadUseCase.execute(new DefaultRequest("Video Start Load"), new SendVideoStartLoadUseCaseSubscriber());
        Call<DefaultResponse> call = apiClient.sendVideoStartLoad(new DefaultRequest("Video Start Load"));

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d(TAG, ">sendVideoStartLoad call - " + call + " _ -> response - " + response);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d(TAG, ">sendVideoStartLoad call - " + call + " _ -> t - " + t.getLocalizedMessage());

            }
        });
    }
    private void notifyVideoFinished(){
        //sendVideoFinishedUseCase.execute(new DefaultRequest("Video Finished"), new SendVideoFinishedUseCaseSubscriber());
        Call<DefaultResponse> call = apiClient.sendVideoFinished(new DefaultRequest("Video Finished"));

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d(TAG, ">sendVideoFinished call - " + call + " _ -> response - " + response);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d(TAG, ">sendVideoFinished call - " + call + " _ -> t - " + t.getLocalizedMessage());

            }
        });
    }

    /*
    //UseCaseSubscribers
    private class SendShowFirstFrameUseCaseSubscriber extends Subscriber<DefaultResponse> {
        @Override
        public void onCompleted() {
            Log.d(TAG, " > SendShowFirstFrame onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, " > SendShowFirstFrame onError - " + e.getLocalizedMessage());
        }

        @Override
        public void onNext(DefaultResponse response) {
            Log.d(TAG, " > SendShowFirstFrame onNext - " + response);
        }
    }

    private class SendVideoStartLoadUseCaseSubscriber extends Subscriber<DefaultResponse> {
        @Override
        public void onCompleted() {
            Log.d(TAG, " > SendVideoStartLoad onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, " > SendVideoStartLoad onError - " + e.getLocalizedMessage());
        }

        @Override
        public void onNext(DefaultResponse response) {
            Log.d(TAG, " > SendVideoStartLoad onNext - " + response);
        }
    }

    private class SendVideoFinishedUseCaseSubscriber extends Subscriber<DefaultResponse> {
        @Override
        public void onCompleted() {
            Log.d(TAG, " > SendVideoFinished onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, " > SendVideoFinished onError - " + e.getLocalizedMessage());
        }

        @Override
        public void onNext(DefaultResponse response) {
            Log.d(TAG, " > SendVideoFinished onNext - " + response);
        }
    }*/
}