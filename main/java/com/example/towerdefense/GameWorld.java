package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

class GameWorld
{
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    private GameStarter gameStarter;
    private Context context;
    private Point size;

    //Collection of game objects go here...
    AlienEnemy enemy;

    //Could save scores here if you wanted...

    //Class constructor
    GameWorld(GameStarter gameStarter, Context context, Point size)
    {
        this.gameStarter = gameStarter;
        this.context = context;
        this.size = size;

        enemy = new AlienEnemy(context, size, "drone");
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
