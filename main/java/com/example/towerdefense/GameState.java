package com.example.towerdefense;

import android.content.Context;

final class GameState
{
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    private GameStarter gameStarter;

    //Could save scores here if you wanted...

    //Class constructor
    GameState(GameStarter gameStarter, Context context)
    {
        this.gameStarter = gameStarter;
    }

    private void endGame()
    {
        mGameOver = true;
        mPaused = true;
    }

    void startNewGame()
    {
        //Don't want to draw while updating game field
        stopDrawing();
        gameStarter.restart();
        resume();

        //Start drawing again
        startDrawing();
    }

    //Collection of various getter and setters.
    void pause() { mPaused = true; }
    void resume() { mGameOver = false; mPaused = false; }
    void startThread() { mThreadRunning = true; }

    boolean getThreadRunning() { return mThreadRunning; }
    boolean getDrawing() { return mDrawing; }
    boolean getPaused() {return mPaused; }
    boolean getGameOver() { return mGameOver; }

    private void stopDrawing() { mDrawing = false; }
    private void startDrawing() { mDrawing = true; }

    void stopEverything()
    {
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }
}
