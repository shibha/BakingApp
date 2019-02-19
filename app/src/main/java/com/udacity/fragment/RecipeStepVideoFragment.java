package com.udacity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.R;

import java.util.logging.Logger;

public class RecipeStepVideoFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView videoView;
    private TextView textView;
    private static MediaSessionCompat mediaSession;
    private static final String TAG = RecipeStepVideoFragment.class.getSimpleName();
    private PlaybackStateCompat.Builder stateBuilder;
    public static final String ARG_RECIPE_VIDEO_URL = "video_url";
    public static final String ARG_RECIPE_STEP_DESC = "step_desc";
    private android.net.Uri videoUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_video, container, false);
        videoView = view.findViewById(R.id.step_video);
        textView = view.findViewById(R.id.step_desc);
        startMedia();
        return view;
    }


    public void startMedia() {
        String videoURL;
        String stepDesc;
        if (getArguments() != null && getArguments().containsKey(ARG_RECIPE_VIDEO_URL)) {
            videoURL = getArguments().getString(ARG_RECIPE_VIDEO_URL);
        } else {
            String videoUrlkey = getActivity().getString(R.string.video_url);
            videoURL = getActivity().getIntent().getStringExtra(videoUrlkey);
        }

        if (getArguments() != null && getArguments().containsKey(ARG_RECIPE_STEP_DESC)) {
            stepDesc = getArguments().getString(ARG_RECIPE_STEP_DESC);
        } else {
            String stepDesckey = getActivity().getString(R.string.step_instruction);
            stepDesc = getActivity().getIntent().getStringExtra(stepDesckey);
        }

        if (textView != null) {
            textView.setText(stepDesc);
        }


        if (videoURL != null && videoView != null) {
            Logger.getLogger(this.getClass().toString()).info("videoView is nOot null" + videoURL + " , " + videoView);
            initializeMediaSession();
            videoUri = Uri.parse(videoURL);
            initializePlayer(videoUri);
        } else {
            Logger.getLogger(this.getClass().toString()).info("videoView is NULLLLLLL null" + videoURL + " , " + videoView);
        }

    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(this.getActivity(), TAG);

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);

        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        mediaSession.setCallback(new MySessionCallback());

        mediaSession.setActive(true);

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }


    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector, loadControl);
            videoView.setPlayer(exoPlayer);

            exoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(this.getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this.getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            Logger.getLogger(this.getClass().toString()).info("RELEASING PLAYER isFirst ");
            releasePlayer();
            mediaSession.setActive(false);
        }

    }


    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }


    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }

    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mediaSession, intent);
        }
    }


}
