package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameEngine extends SurfaceView implements Runnable, GameStarter, GameEngineBroadcaster
{
    //Class Variables
    private GameView mGameView;
    private GameWorld mGameWorld;
    private HUD mHUD;
    private Context context;
    private Thread mThread = null;
    private Point size;
    private long mFPS;
    private long mNextFrameTime, mLastFrameTime;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;
    final static long TARGET_FPS = 30;
    private int currentWave = 1;
    final long MILLIS_PER_SECOND = 1000;

    //Class constructor
    public GameEngine(Context context, Point size)
    {
        super(context);
        mNextFrameTime=System.currentTimeMillis();
        this.context = context;
        this.size = size;
        mGameWorld = new GameWorld(this, this.context, size);
        mGameView = new GameView(this, this.context, size);
        mHUD= new HUD(size);
        mHUD.setGraphics(context);
        mGameWorld.mTowers=new ArrayList<Tower>();
        mGameWorld.mPlasmaProjectiles =new ArrayList<PlasmaProjectile>();
        mGameWorld.mAliens=new ArrayList<Alien>();
        mGameWorld.mLasers=new ArrayList<Laser>();
        //mGameWorld.mAliens.add(new AlienFactory(context, size, "drone").getAlien());
        mGameWorld.setLives();
        mGameWorld.resetCash();
        mGameWorld.mMap = new GameMap(context, size);
        mUIController = new UIController(this, context);
        mGameWorld.range=null;
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

            //Update 10 times a second
            if (updateRequired())
            {
                update();
                if (mGameWorld.mAliens.isEmpty())
                {

                    mGameWorld.mAliens = mGameWorld.mMap.spawn(context, currentWave);

                    //If the number of waves is greater than the number of waves for the map. You won.
                    if (currentWave > mGameWorld.mMap.getWaveCount())
                        mGameWorld.endGame();

                    currentWave++;
                }
                //Draw all game objects here...
                mGameView.draw(mGameWorld, mHUD, mFPS);

            }
        }
    }

    //Updates all the game objects in play
    private void update()
    {
        if(!mGameWorld.getPaused())
        {
            ArrayList<PlasmaProjectile> projectilesToRemove = new ArrayList<>();
            if (mGameWorld.mPlasmaProjectiles != null)
            {
                Iterator<PlasmaProjectile> projectileIterator = mGameWorld.mPlasmaProjectiles.iterator();
                while(projectileIterator.hasNext())
                {
                    PlasmaProjectile plasmaProjectile = projectileIterator.next();
                    plasmaProjectile.move();
                    if(plasmaProjectile.collision(mGameWorld))
                    {
                        projectilesToRemove.add(plasmaProjectile);
                    }
                }
            }

            //Remove dead aliens
            Iterator<Alien> alienIterator = mGameWorld.mAliens.iterator();
            while(alienIterator.hasNext())
            {
                if (alienIterator.next().getHealth() <= 0)
                {
                    alienIterator.remove();
                    mGameWorld.addCash();
                }

            }

            //Check for aliens that hit the base
            alienIterator = mGameWorld.mAliens.iterator();
            while(alienIterator.hasNext())
            {
                if (alienIterator.next().checkCollision(mGameWorld.mMap.getBaseCords()))
                {
                    alienIterator.remove();
                    mGameWorld.loseLife();
                }

            }

            //Update aliens
            alienIterator = mGameWorld.mAliens.iterator();
            while(alienIterator.hasNext())
            {
                alienIterator.next().move();
            }

            //Remove projectiles
            Iterator<PlasmaProjectile> projectileMoveableIterator = projectilesToRemove.iterator();
            while(projectileMoveableIterator.hasNext())
            {
                mGameWorld.mPlasmaProjectiles.remove(projectileMoveableIterator.next());
            }

            //Tell the towers to fire
            if (mGameWorld.mTowers != null)
            {
                Iterator<Tower> towerIterator = mGameWorld.mTowers.iterator();
                while(towerIterator.hasNext()){
                    towerIterator.next().shoot(mGameWorld);
                }

            }

            ArrayList<Laser> lasersToRemove = new ArrayList<>();
            if (mGameWorld.mPlasmaProjectiles != null)
            {
                Iterator<Laser> laserIterator = mGameWorld.mLasers.iterator();
                while(laserIterator.hasNext())
                {
                    Laser laser= laserIterator.next();
                    if(laser.done())
                    {
                        lasersToRemove.add(laser);
                    }
                }
            }

            //Remove lasers
            Iterator<Laser> LaserToRemoveIterator = lasersToRemove.iterator();
            while(LaserToRemoveIterator.hasNext())
            {
                mGameWorld.mLasers.remove(LaserToRemoveIterator.next());
            }
        }
    }

    //Method is used to check when an update is required to the GameView
    public boolean updateRequired()
    {

        //Are we due to update the frame
        if(mNextFrameTime<= System.currentTimeMillis())
        {
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            //Measure FPS
            long time=System.currentTimeMillis()-mLastFrameTime;
            if(time>=1)
            {
                mFPS = MILLIS_PER_SECOND / (time);
                mLastFrameTime=System.currentTimeMillis();
            }

            //Return true so that the update and draw
            return true;
        }

        //Else, no update is required.
        return false;
    }

    @Override
    public boolean onTouchEvent (MotionEvent motionEvent)
    {
        //Handle player input
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameWorld, mHUD.getControls());
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
        mGameWorld.resetCash();
        mGameWorld.setLives();
        mGameWorld.mAliens = new ArrayList<>();
        mGameWorld.mTowers = new ArrayList<>();
        mGameWorld.mPlasmaProjectiles = new ArrayList<>();
        currentWave = 1;
    }

    public void addObserver(InputObserver o)
    {
        inputObservers.add(o);
    }
}
