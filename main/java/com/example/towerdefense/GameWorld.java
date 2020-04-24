package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;

class GameWorld
{
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    private static volatile boolean mPlacing = false;
    private static volatile boolean mGameWon = false;
    private static volatile boolean mGameRunning = false;
    private Tower.TowerType mTowerType;

    final static long BASE_TICKS_PER_SECOND=40, FPS=30;

    private long ticksPerSecond;
    private boolean fastGame;

    private GameStarter gameStarter;
    private Context context;
    private Point size;

    //Collection of game objects go here...
    ArrayList<Projectile> mProjectiles;
    ArrayList<Tower> mTowers;
    ArrayList<Alien> mAliens;
    Circle range;
    GameMap mMap;
    StartScreen startScreen;


    //Game variables
    private int mLives;
    private int mCash;

    //Class constructor
    GameWorld(GameStarter gameStarter, Context context, Point size)
    {
        this.startScreen=new StartScreen(context, size);
        this.gameStarter = gameStarter;
        this.context = context;
        this.size = size;
        fastGame=false;
        ticksPerSecond=BASE_TICKS_PER_SECOND;
    }

    public void endGame()
    {
        mGameOver = true;
        mPaused = true;

        //If you still have lives, you won!
        if (mLives > 0)
            mGameWon = true;
    }

    public ArrayList<Tower> getTowers(){
        return mTowers;
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
    public void setmPlacing() { mPlacing=true;}
    public void closemPlacing(){ mPlacing=false;}
    public void setTowerType(Tower.TowerType type){mTowerType=type;}
    public Tower.TowerType getTowerType(){ return mTowerType;}
    public boolean getmPlacing(){ return mPlacing;}
    public boolean getmGameWon() { return mGameWon; }
    public boolean getGameRunning(){ return mGameRunning;}
    public void setGameRunningOn(){ mGameRunning=true;}
    public void setGameRunningOff(){ mGameRunning=true;}

    public void addTower(Tower tower)
    {
        mTowers.add(tower);
    }


    void stopEverything()
    {
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    public int getLives() {
        return mLives;
    }
    public void setLives(){
        mLives=20;
    }
    public int getCash(){ return mCash;}
    public void resetCash(){this.mCash=500;}
    public void addCash(){this.mCash+=10;}
    public void loseCash(int amount){this.mCash-=amount;}

    public void loseLife() {
        mLives -= 1;

        //Check for death
        if (mLives <= 0)
            endGame();
    }

    public void changeSpeed(){
        if(!fastGame){
            ticksPerSecond=5*BASE_TICKS_PER_SECOND;
            fastGame=true;
        }else{
            fastGame=false;
            ticksPerSecond=BASE_TICKS_PER_SECOND;
        }
    }

    public int getSpeed(){
        if(fastGame){
            return 5;
        }else{
            return 1;
        }
    }

    public void resetSpeed(){
        fastGame=false;
    }

    public void addCashAmmount(int mCash){
        this.mCash+=mCash;
    }

    public Tower onTower(Point location){
        Tower tower=null;
        if(mTowers != null){
            Iterator<Tower> towerIterator = mTowers.iterator();
            while(towerIterator.hasNext())
            {
                Tower currentTower = towerIterator.next();
                if (currentTower.contains(location)){
                    tower=currentTower;
                    break;
                }
            }
        }
        return tower;
    }
    public boolean overTower(Point location){
        Tower temp = onTower(location);
        return (temp != null);
    }


}
