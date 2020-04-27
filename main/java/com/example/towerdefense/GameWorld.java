package com.example.towerdefense;

import android.content.Context;
import android.graphics.Color;
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
    private static volatile boolean mReadyForNewGame = false;
    private Tower.TowerType mTowerType;

    static final int white = Color.argb(255,255,255,255);
    static final int black = Color.argb(255,0,0,0);
    static final int red = Color.argb(255,255,0,0);
    static final int orange = Color.argb(150,255,102,0);

    final static long BASE_TICKS_PER_SECOND=40, FPS=30;

    private long ticksPerSecond;
    public String errorMessage;
    private final int SPEED_MULTIPLIER=5;

    public long errorTime;

    private boolean fastGame;

    private GameStarter gameStarter;
    private Context context;
    private Point size;

    public enum error{MONEY, PLACEMENT}

    //Create sound manager.
    public GameSound mSound;

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
        mSound = new GameSound(context);
    }

    public void endGame()
    {
        mGameOver = true;
        mPaused = true;

        //If you still have lives, you won!
        if (mLives > 0)
            mGameWon = true;
    }
    public void error(GameWorld.error error){
        errorTime=1000+System.currentTimeMillis();
        switch (error){
            case MONEY:
                errorMessage="Not enough money";
                break;
            case PLACEMENT:
                errorMessage="Cannot place a tower here";
                break;
        }

    }


    void startGame()
    {
        //Don't want to draw while updating game field
        stopDrawing();

        resume(); //unpause the game

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
    public void setGameRunningOn(){
        mGameRunning=true;
        mSound.playSoundTrack();
    }
    public void setGameRunningOff(){
        mGameRunning=false;
        mSound.stopSoundTrack();
    }

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
            ticksPerSecond=SPEED_MULTIPLIER*BASE_TICKS_PER_SECOND;
            fastGame=true;
        }else{
            fastGame=false;
            ticksPerSecond=BASE_TICKS_PER_SECOND;
        }
    }

    public int getSpeed(){
        if(fastGame){
            return SPEED_MULTIPLIER;
        }else{
            return 1;
        }
    }

    public void resetSpeed(){
        fastGame=false;
    }

    public void addCashAmmount(int mCash){
        this.mCash+=mCash;
    } //add a specific amount of cash

    public Tower getTower(Point location){ //returns the tower that is situated at that point
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
    public boolean overTower(Point location){ //returns true if the point is located on a tower
        Tower temp = getTower(location);
        return (temp != null);
    }


    public void loadLevel(GameMap.level level){ //load the current level
        resetCash();
        setLives();
        mAliens = new ArrayList<>();
        mTowers = new ArrayList<>();
        mProjectiles = new ArrayList<>();
        mMap.setCurrentWave(1);
        resetSpeed();
        mReadyForNewGame=true;
        mGameRunning=true;
        mSound.playSoundTrack();
        mMap.changeLevel(level);
        mGameWon=false;
    }

    public void setReadyForNewGameTrue(){
        mReadyForNewGame=true;
    }
    public void setReadyForNewGameFalse(){
        mReadyForNewGame=false;
    }
    public boolean getReadyForNewGame(){
        return mReadyForNewGame;
    }

    public int getCurrentTowerCost(){
        int cost=0;
        if(mPlacing && mTowerType==Tower.TowerType.LASER){
            cost = Tower.LASER_COST;
        }
        else if(mPlacing && mTowerType==Tower.TowerType.PLASMA){
            cost = Tower.PLASMA_COST;
        }
        else if(mPlacing && mTowerType==Tower.TowerType.ROCKET){
            cost = Tower.ROCKET_COST;
        }
        return cost;
    }


}
