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
    private long mNextFrameTime;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;
    final static long TARGET_FPS = 30;

    //Class constructor
    public GameEngine(Context context, Point size)
    {
        super(context);
        mNextFrameTime=System.currentTimeMillis();
        this.context = context;
        this.size = size;
        mGameWorld = new GameWorld(this, this.context, size);
        mGameView = new GameView(this, this.context);
        mHUD= new HUD(size);
        mHUD.setGraphics(context);
        mGameWorld.mTowers=new ArrayList<Tower>();
        mGameWorld.mProjectiles=new ArrayList<Projectile>();
        mGameWorld.mAliens=new ArrayList<AlienEnemy>();
        mGameWorld.mAliens.add(new AlienEnemy(context, size, "drone"));
        mGameWorld.setLives();
        mGameWorld.resetCash();
        mUIController = new UIController(this, context);
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
            //mGameWorld.mTowers.add(new Tower(context,size));
            //System.out.println("running");
            //Update 10 times a second
            if (updateRequired())
            {
                update();
            }




            //Draw all game objects here...
            mGameView.draw(mGameWorld, mHUD);

            //Measure FPS
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;

            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }
    private void update(){
        //System.out.println("updatting");
        //System.out.println(mGameWorld.mTowers.size());
        //System.out.println(mGameWorld.mTowers != null);
        //Update all game objects here...
        if(!mGameWorld.getPaused()){
            ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
            if (mGameWorld.mProjectiles != null){
                Iterator<Projectile> projectileIterator = mGameWorld.mProjectiles.iterator();
                while(projectileIterator.hasNext()){
                    Projectile projectile= projectileIterator.next();
                    projectile.move();
                    if(projectile.collision(mGameWorld)){
                        projectilesToRemove.add(projectile);
                    }
                }
            }

            //remove projectiles
            Iterator<Projectile> projectileMoveableIterator = projectilesToRemove.iterator();
            while(projectileMoveableIterator.hasNext()){
                mGameWorld.mProjectiles.remove(projectileMoveableIterator.next());

            }

            if (mGameWorld.mTowers != null){
                Iterator<Tower> towerIterator = mGameWorld.mTowers.iterator();
                while(towerIterator.hasNext()){
                    towerIterator.next().shoot(mGameWorld);
                }

            }
        }

    }
    public boolean updateRequired()
    {

        //There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        //Are we due to update the frame
        if(mNextFrameTime<= System.currentTimeMillis())
        {
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

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

    public void restart()
    {
        //This method will despawn and respawn all game objects.
        //Called to restart the game.
    }
    public void addObserver(InputObserver o) {

        inputObservers.add(o);
    }
}
