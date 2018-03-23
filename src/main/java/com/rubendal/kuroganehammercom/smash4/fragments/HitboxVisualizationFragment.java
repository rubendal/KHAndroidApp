package com.rubendal.kuroganehammercom.smash4.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.adapter.AnimationSpinnerAdapter;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.smash4.classes.MoveAnimation;

import java.util.LinkedList;
import java.util.List;

public class HitboxVisualizationFragment extends KHFragment {

    private Character character;
    private List<MoveAnimation> animations;
    private VideoView videoView;
    private MediaController mediaController;
    private ProgressDialog dialog;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;

    private EditText frameCounterView;

    private final int frameSpeed = 1000/60;
    private int totalFrames = 1;

    public HitboxVisualizationFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (Character)getArguments().getSerializable("character");
            this.animations = (List<MoveAnimation>)getArguments().getSerializable("animations");
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.update).setVisible(false);
    }

    public static HitboxVisualizationFragment newInstance(Character character, LinkedList<MoveAnimation> animations){
        HitboxVisualizationFragment fragment = new HitboxVisualizationFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        args.putSerializable("animations", animations);
        fragment.setArguments(args);
        fragment.character = character;
        fragment.animations = animations;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hitbox_visualization, container, false);

        videoView = (VideoView)view.findViewById(R.id.videoView);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                if(dialog.isShowing())
                    dialog.dismiss();

                totalFrames = (int)Math.floor((double)videoView.getDuration() * 60 / 1000);

                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        videoView.refreshDrawableState(); //Update frame on VideoView

                        if(frameCounterView.getTag() != null){

                            int frame = (int) Math.ceil((double) mp.getCurrentPosition() * 60 / 1000) + 1;

                            frameCounterView.setText(String.valueOf(frame));

                            frameCounterView.setTag(null);
                        }
                    }
                });

                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.INVISIBLE);

                PlayVideo();
                PauseVideo();


            }
        });


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.INVISIBLE);
            }
        });

        mediaController = new MediaController(getContext());

        //mediaController.setAnchorView(videoView);
        //videoView.setMediaController(mediaController);

        playButton = (ImageButton)view.findViewById(R.id.play);
        pauseButton = (ImageButton)view.findViewById(R.id.pause);
        prevButton = (ImageButton)view.findViewById(R.id.previous);
        nextButton = (ImageButton)view.findViewById(R.id.next);

        frameCounterView = (EditText) view.findViewById(R.id.frameCounter);

        frameCounterView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(view.getTag() != null)
                    return;

                try{
                    int frame = Integer.parseInt(s.toString());
                    int currentFrame = (int)Math.ceil((double)videoView.getCurrentPosition() * 60 / 1000) + 1;
                    if(frame == currentFrame)
                        return;
                    if(frame >= 1 && frame <= totalFrames){
                        if(videoView.isPlaying()) {
                            PauseVideo();
                            playButton.setVisibility(View.VISIBLE);
                            pauseButton.setVisibility(View.INVISIBLE);
                        }

                        videoView.seekTo((int)Math.floor((double)(frame-1) * 1000 / 60));

                    }else{
                        /*
                        view.setTag("");
                        if(frame < 1){
                            s.clear();
                            s.append("1");
                        }else if(frame > totalFrames){
                            s.clear();
                            s.append(String.valueOf(totalFrames));
                        }
                        view.setTag(null);
                        */
                    }

                }catch(Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!videoView.isPlaying()) {
                    PlayVideo();
                    pauseButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.INVISIBLE);
                }
            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()) {
                    PauseVideo();
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()) {
                    PauseVideo();
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.INVISIBLE);
                }
                frameCounterView.setTag("");
                if(videoView.getCurrentPosition()-frameSpeed > 0)
                    videoView.seekTo(videoView.getCurrentPosition() - frameSpeed);
                else
                    videoView.seekTo(0);

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()) {
                    PauseVideo();
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.INVISIBLE);
                }
                frameCounterView.setTag("");
                if(videoView.getCurrentPosition()+frameSpeed <= videoView.getDuration()) {
                    videoView.seekTo(videoView.getCurrentPosition() + frameSpeed);
                }else{
                    videoView.seekTo(videoView.getDuration()-1);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void updateData() {

    }

    @Override
    public String getTitle() {
        return String.format("%s/Hitboxes", character.getCharacterTitleName());
    }

    private void loadData(){

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        Spinner spinner = (Spinner)getView().findViewById(R.id.animationSpinner);
        spinner.setAdapter(new AnimationSpinnerAdapter(getContext(),animations));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MoveAnimation animation = animations.get(position);

                loadVideo(animation.rawName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadVideo(animations.get(0).rawName);
    }

    public void loadVideo(String animation){
        dialog.setTitle("Loading");
        dialog.show();
        videoView.setVideoURI(Uri.parse(character.getCharacterHitboxVisualizationVideo(animation)));
    }

    private void PlayVideo(){
        frameCounterView.setTag("");
        videoView.start();
        frameCounterView.setText("");
        frameCounterView.setTag(null);
    }

    private void PauseVideo(){
        frameCounterView.setTag("");
        videoView.pause();
        int frame = (int)Math.ceil((double)videoView.getCurrentPosition() * 60 / 1000) + 1;

        frameCounterView.setText(String.valueOf(frame));
        frameCounterView.setTag(null);
    }
}
