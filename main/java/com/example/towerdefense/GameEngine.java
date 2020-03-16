package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements Runnable, GameStarter
{
    //Class Variables
    private Renderer mRenderer;
    private GameState mGameState;
    private Thread mThread = null;
    private long mFPS;

    //Class constructor
    public GameEngine(Context context, Point size)
    {
        super(context);

        mGameState = new GameState(this, context);
        mRenderer = new Renderer(this);
    }

    @Override
    public void run()
    {
        while (mGameState.getThreadRunning())
        {
            long frameStartTime = System.currentTimeMillis();

            if (!mGameState.getPaused()) {
                //The game is paused...
            }

            //Update all game objects here...
            mRenderer.draw(mGameState);

            //Measure FPS
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;

            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent motionEvent)
    {
        //Handle player input

        return true;
    }

    public void stopThread()
    {
        //More code will be needed...
        mGameState.stopEverything();

        try
        {
            mThread.join();
        }
        catch (InterruptedException e)
        {
            Log.e("Exception", "stopThread()" + e.getMessage());
        }
    }

    public void startThread()
    {
        //More code will be needed...
        mGameState.startThread();

        mThread = new Thread(this);
        mThread.start();
    }

    public void restart()
    {
        //This method will despawn and respawn all game objects.
        //Called to restart the game.
    }
}
