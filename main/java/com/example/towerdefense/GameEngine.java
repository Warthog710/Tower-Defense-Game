package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements Runnable, GameStarter
{
    //Class Variables
    private GameView mGameView;
    private GameWorld mGameWorld;
    private Context context;
    private Thread mThread = null;
    private Point size;
    private long mFPS;

    //Class constructor
    public GameEngine(Context context, Point size)
    {
        super(context);

        this.context = context;
        this.size = size;
        mGameWorld = new GameWorld(this, this.context, size);
        mGameView = new GameView(this, this.context);
    }

    @Override
    public void run()
    {
        while (mGameWorld.getThreadRunning())
        {
            long frameStartTime = System.currentTimeMillis();

            if (!mGameWorld.getPaused()) {
                //The game is paused...
            }

            //Update all game objects here...

            //Draw all game objects here...
            mGameView.draw(mGameWorld);

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
        mGameWorld.stopEverything();

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
        mGameWorld.startThread();

        mThread = new Thread(this);
        mThread.start();
    }

    public void restart()
    {
        //This method will despawn and respawn all game objects.
        //Called to restart the game.
    }
}
