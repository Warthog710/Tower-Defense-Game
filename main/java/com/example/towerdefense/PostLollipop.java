package com.example.towerdefense;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

import java.io.IOException;

//Set target API to 21 (Post Lollipop)
@TargetApi(21)
public class PostLollipop implements ISound
{
    private SoundPool mSP;
    private int mLaser_ID, mPlasma_ID, mRocket_ID, mSoundTrack_ID;
    private MediaPlayer mediaPlayer;

    //Initialize SoundPool
    PostLollipop(Context context)
    {
        AudioAttributes audioAttributes =
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes
                                .CONTENT_TYPE_SONIFICATION)
                        .build();
        mSP = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        loadAssets(context);


        mediaPlayer = MediaPlayer.create(context, R.raw.sound_track);
    }

    //Load sound assets into SoundPool
    private void loadAssets(Context context)
    {
        try
        {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("laser_sound.ogg");
            mLaser_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("rocket_sound.ogg");
            mRocket_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("plasma_sound.ogg");
            mPlasma_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("sound_track.ogg");
            mSoundTrack_ID = mSP.load(descriptor, 0);
    }
        catch (IOException e)
        {
            System.out.println("Failed to load sound assets.");
        }
    }


    @Override
    public void playLaser() {
        mSP.play(mLaser_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playRocket() {
        mSP.play(mRocket_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playPlasma() {
        mSP.play(mPlasma_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playSoundTrack() {
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    @Override
    public void stopSoundTrack() {
        mediaPlayer.pause();
        mediaPlayer.setLooping(false);
    }
}
