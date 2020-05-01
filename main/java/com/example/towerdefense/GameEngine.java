package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameEngine extends SurfaceView implements Runnable, GameEngineBroadcaster
{
    //Class Variables
    private GameView mGameView;
    private GameWorld mGameWorld;
    private HUD mHUD;
    private Context context;
    private Thread mThread = null;
    private Point size;
    private long mFPS;
    private long mNextFrameTime, mLastFrameTime, mNextDraw;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;
    final long MILLIS_PER_SECOND = 1000;
    //final static long TARGET_FPS=40;

    //Class constructor
    public GameEngine(Context context, Point size)
    {
        super(context);
        mNextFrameTime=mNextDraw=System.currentTimeMillis();
        this.context = context;
        this.size = size;
        mGameWorld = new GameWorld(this.context, size);
        mGameView = new GameView(this, this.context, size);
        mHUD= new HUD(size);
        mHUD.setGraphics(context);
        mUIController = new UIController(this, context);
    }

    @Override
    public void run()
    {
        while (mGameWorld.getThreadRunning())
        {

            if (!mGameWorld.getPaused())
            {
                //The game is paused...
            }

            if (updateRequired() && !mGameWorld.getPaused())
            {
                //Called to update all gameObjects
                update();

                //If the game is running
                if (mGameWorld.getGameRunning())
                {
                    //Check to see if the last wave has occuerd. If so, end the game.
                    if (mGameWorld.enemySpawner.getCurrentWave() >= mGameWorld.enemySpawner.getWaveCount())
                    {
                        mGameWorld.endGame();
                    }
                }

            }
            if(mNextDraw<= System.currentTimeMillis())
            {
                //Measure FPS
                long time=System.currentTimeMillis()-mLastFrameTime;

                if(time>=1)
                {
                    mFPS = MILLIS_PER_SECOND / (time);
                    mLastFrameTime=System.currentTimeMillis();
                }

                mNextDraw =System.currentTimeMillis()
                        + MILLIS_PER_SECOND / GameWorld.FPS;

                //Draw all game objects here...
                mGameView.draw(mGameWorld, mHUD, mFPS);

            }

        }
    }

    //Updates all the game objects in play
    private void update()
    {
        //Check if game is paused. If not, executed the following...
        if(!mGameWorld.getPaused())
        {
            ArrayList<Projectile> projectilesToRemove = new ArrayList<>();

            if (mGameWorld.mProjectiles != null)
            {
                Iterator<Projectile> projectileIterator = mGameWorld.mProjectiles.iterator();

                while(projectileIterator.hasNext())
                {
                    Projectile projectile = projectileIterator.next();

                    //Move projectiles
                    projectile.move();

                    //check if the projectile has hit something and needs to be removed
                    if(projectile.remove(mGameWorld))
                    {
                        projectilesToRemove.add(projectile);
                    }
                }
            }

            //Remove dead aliens
            Iterator<Alien> alienIterator = mGameWorld.mAliens.iterator();

            while(alienIterator.hasNext())
            {
                Alien temp = alienIterator.next();

                //If health < 0, remove enemy and add cash.
                if (temp.getStatus())
                {
                    alienIterator.remove();
                    mGameWorld.addCashAmount(temp.getMoney());
                }
            }

            //Check for aliens that hit the base
            alienIterator = mGameWorld.mAliens.iterator();

            while(alienIterator.hasNext())
            {
                Alien temp = alienIterator.next();

                if (temp.checkCollision(mGameWorld.mMap.getBaseCords()))
                {
                    //Set health to zero, and remove.
                    temp.kill();
                    alienIterator.remove();
                    mGameWorld.loseLife();
                }
            }

            //Update aliens
            alienIterator = mGameWorld.mAliens.iterator();

            //Move all active aliens
            while(alienIterator.hasNext())
            {
                alienIterator.next().move(mGameWorld.mMap.getPathCords());
            }

            //Remove projectiles
            Iterator<Projectile> projectilesToRemoveIterator = projectilesToRemove.iterator();
            while(projectilesToRemoveIterator.hasNext())
            {
                mGameWorld.mProjectiles.remove(projectilesToRemoveIterator.next());
            }

            //Tell the towers to fire
            if (mGameWorld.mTowers != null)
            {
                Iterator<Tower> towerIterator = mGameWorld.mTowers.iterator();

                while(towerIterator.hasNext())
                {
                    towerIterator.next().shoot(mGameWorld);
                }
            }

            //Spawn more enemies if needed.
            //mGameWorld.mAliens = mGameWorld.mMap.spawn(context, mGameWorld.mAliens);
            mGameWorld.mAliens = mGameWorld.enemySpawner.spawn(mGameWorld.mAliens, mGameWorld.mMap.getPathCords().get(0));
        }
    }

    //Method is used to check when an update is required to the GameView
    public boolean updateRequired()
    {

        //Are we due to update the frame
        if(mNextFrameTime<= System.currentTimeMillis())
        {
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / (GameWorld.BASE_TICKS_PER_SECOND*mGameWorld.getSpeed());

            //Return true so that the update and draw
            return true;
        }

        //Else, no update is required.
        return false;
    }

    //Handles player input
    @Override
    public boolean onTouchEvent (MotionEvent motionEvent)
    {
        for (InputObserver o : inputObservers)
        {
            o.handleInput(motionEvent, mGameWorld, mHUD);
        }

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
        mNextFrameTime = System.currentTimeMillis();

        mThread = new Thread(this);
        mThread.start();
    }

    //Called to restart the game.
    public void restart()
    {
        //This method will despawn and respawn all game objects.
        System.out.println("nothing");
    }

    public void addObserver(InputObserver o)
    {
        inputObservers.add(o);
    }
}
