package com.example.towerdefense;

//This strategy can only be chosen by setting the silent mode to true when the game sound object is
//created. Intended for debugging purposes.
public class SilentStrategy implements ISound
{
    @Override
    public void playLaser() {

    }

    @Override
    public void playRocket() {

    }

    @Override
    public void playPlasma() {

    }

    @Override
    public void playSoundTrack() {

    }

    @Override
    public void stopSoundTrack() {

    }
}
