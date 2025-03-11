package com.example.creative_learn.ui.video;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.creative_learn.R;
import com.example.creative_learn.database.DatabaseHelper;
import com.example.creative_learn.utils.VideoHelper;

public class VideoPlayerFragment extends Fragment {
    private ExoPlayer player;
    private PlayerView playerView;
    private TextView titleText, descriptionText;
    private ProgressBar videoLoadingProgress;
    private DatabaseHelper databaseHelper;
    private String currentVideoId;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video_player, container, false);

        playerView = root.findViewById(R.id.videoPlayer);
        titleText = root.findViewById(R.id.videoTitle);
        descriptionText = root.findViewById(R.id.videoDescription);
        videoLoadingProgress = root.findViewById(R.id.videoLoadingProgress);

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String description = getArguments().getString("description");
            String videoFileName = getArguments().getString("videoFileName");
            int videoResourceId = getArguments().getInt("videoResourceId");

            titleText.setText(title);
            descriptionText.setText(description);
            playVideo(videoFileName, videoResourceId);
        }

        return root;
    }

    private void playVideo(String videoFileName, int videoResourceId) {
        if (!VideoHelper.isVideoDownloaded(requireContext(), videoFileName)) {
            mainHandler.post(() -> videoLoadingProgress.setVisibility(View.VISIBLE));
            
            VideoHelper.setProgressListener(progress -> {
                mainHandler.post(() -> {
                    videoLoadingProgress.setProgress(progress);
                    if (progress == 100) {
                        videoLoadingProgress.setVisibility(View.GONE);
                        startVideoPlayback(videoFileName);
                    }
                });
            });

            new Thread(() -> {
                VideoHelper.copyVideoFromRawToInternal(requireContext(), 
                    videoResourceId, 
                    videoFileName);
            }).start();
        } else {
            startVideoPlayback(videoFileName);
        }
    }

    private void startVideoPlayback(String videoFileName) {
        mainHandler.post(() -> {
            if (player == null) {
                player = new ExoPlayer.Builder(requireContext()).build();
                playerView.setPlayer(player);
            }

            String videoPath = VideoHelper.getVideoPath(requireContext(), videoFileName);
            MediaItem mediaItem = MediaItem.fromUri("file://" + videoPath);
            
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
} 