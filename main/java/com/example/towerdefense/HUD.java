package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.ArrayList;

class HUD
{
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<Rect> controls;
    public TowerInfo towerInfo;
    public AlienInfo alienInfo;
    public PlacingInfo placingInfo;
    static int PAUSE = 0;
    static int SPEEDUP = 1;
    static int PlasmaTower=2;
    static int LaserTower=3;
    static int RocketTower=4;
    static int white = Color.argb(255,255,255,255);
    static int black = Color.argb(255,0,0,0);
    private Bitmap mPauseBitMap, mPlasmaTower, mLaserTower, mRocketTower, mSpeedUpBitMap, mWonSplashScreen, mLostSplashScreen;
    private Bitmap mPausedSplashScreen, mLevel1SplashScreen, mLevel2SplashScreen, mLevel3SplashScreen;
    private Point mPauseLocation, mPlasmaTowerLocation, mLaserTowerLocation, mRocketTowerLocation, mSpeedUpLocation;
    private int buttonWidth;
    private int buttonHeight;
    private Point size;
    private int mSplashScreenSize=600;

    HUD(Point size)
    {
        this.size=size;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        controls= new ArrayList<Rect>();
        prepareControls();
        towerInfo=null;
        alienInfo=null;
        placingInfo=null;
    }

    private void prepareControls()
    {
        int buttonPadding = mScreenWidth / 90;
        Rect pause = new Rect( //pause button
                mScreenWidth - buttonPadding - buttonWidth, buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);
        Rect speedUp = new Rect( //tower button
                mScreenWidth - (buttonPadding*2) - (buttonWidth*2),
                buttonPadding,
                mScreenWidth - buttonPadding*2-buttonWidth,
                buttonPadding + buttonHeight);
        Rect plasmaTower = new Rect( //tower button
                mScreenWidth - (buttonPadding*3) - (buttonWidth*3),
                buttonPadding,
                mScreenWidth - buttonPadding*3-buttonWidth*2,
                buttonPadding + buttonHeight);
        Rect laserTower = new Rect( //tower button
                mScreenWidth - (buttonPadding*4) - (buttonWidth*4),
                buttonPadding,
                mScreenWidth - buttonPadding*4-buttonWidth*3,
                buttonPadding + buttonHeight);
        Rect rocketTower = new Rect( //tower button
                mScreenWidth - (buttonPadding*5) - (buttonWidth*5),
                buttonPadding,
                mScreenWidth - buttonPadding*5-buttonWidth*4,
                buttonPadding + buttonHeight);

        controls.add(PAUSE, pause);
        controls.add(SPEEDUP, speedUp);
        controls.add(PlasmaTower, plasmaTower);
        controls.add(LaserTower, laserTower);
        controls.add(RocketTower, rocketTower);


        mPauseLocation= new Point(mScreenWidth - buttonPadding - buttonWidth,buttonPadding); //change this just use the rectangles location instead
        mSpeedUpLocation= new Point(mScreenWidth - (buttonPadding*2) - (buttonWidth*2),buttonPadding); //use r.left and r.top
        mPlasmaTowerLocation= new Point(mScreenWidth - (buttonPadding*3) - (buttonWidth*3), buttonPadding);
        mLaserTowerLocation= new Point(mScreenWidth - (buttonPadding*4) - (buttonWidth*4), buttonPadding);
        mRocketTowerLocation= new Point(mScreenWidth - (buttonPadding*5) - (buttonWidth*5), buttonPadding);

    }

    void draw(Canvas c, Paint p, GameWorld mGameWorld, long FPS) //draw the HUD
    {
        p.setColor(black);
        p.setTextSize(mTextFormatting);

        c.drawText("Lives: " + mGameWorld.getLives(), //draw lives
                mTextFormatting, mTextFormatting * 2,p);
        c.drawText("Cash: " + mGameWorld.getCash(), //draw cash
                mTextFormatting*6, mTextFormatting * 2,p);

        //TEMPORARY
        c.drawText("FPS: " + FPS, //draw FPS
                mTextFormatting*12, mTextFormatting * 2,p);

        if(mGameWorld.getGameOver()) //if the game is over
        {
            //If you don't have any lives you must've lost
            if (mGameWorld.getLives() <= 0)
            {
                c.drawBitmap(mLostSplashScreen, (mScreenWidth/2)-(mSplashScreenSize/2), (mScreenHeight/2)-(mSplashScreenSize/2), null);

            }
            //If gameWon is true you must've won
            else if (mGameWorld.getmGameWon())
            {
                c.drawBitmap(mWonSplashScreen, (mScreenWidth/2)-(mSplashScreenSize/2), (mScreenHeight/2)-(mSplashScreenSize/2), null);

            }
            //Must be a new game...
            else
            {
                switch (mGameWorld.mMap.getCurrentLevel()){
                    case 1:
                        c.drawBitmap(mLevel1SplashScreen, (mScreenWidth/2)-(mSplashScreenSize/2), (mScreenHeight/2)-(mSplashScreenSize/2), null);
                        break;
                    case 2:
                        c.drawBitmap(mLevel2SplashScreen, (mScreenWidth/2)-(mSplashScreenSize/2), (mScreenHeight/2)-(mSplashScreenSize/2), null);
                        break;
                    case 3:
                        c.drawBitmap(mLevel3SplashScreen, (mScreenWidth/2)-(mSplashScreenSize/2), (mScreenHeight/2)-(mSplashScreenSize/2), null);
                        break;

                }
            }
        }

        if(mGameWorld.getPaused() && !mGameWorld.getGameOver()) //if the game is paused
        {
            c.drawBitmap(mPausedSplashScreen, (mScreenWidth/2)-(mSplashScreenSize/2), (mScreenHeight/2)-(mSplashScreenSize/2), null);

        }

        drawControls(c, p); //draw the buttons
        drawGraphics(c); //draw the graphics over the buttons

        if (towerInfo !=null){
            towerInfo.draw(c,p);
        } else if (alienInfo !=null){
            alienInfo.draw(c,p);
        } else if (placingInfo != null){
            placingInfo.draw(c,p);
        }
    }

    private void drawControls(Canvas c, Paint p)
    {
        p.setColor(black); //set color to black
        for(Rect r : controls)
        {
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

        // Set the color to white
        p.setColor(white);
    }
    ArrayList<Rect> getControls(){
        return controls;
    }

    public void setGraphics(Context context) //add graphics to the buttons
    {
        HelperFactory helperFactory = new HelperFactory(context);
        this.mPauseBitMap=helperFactory.bitmapMaker(R.drawable.play, buttonWidth, buttonHeight);
        this.mSpeedUpBitMap=helperFactory.bitmapMaker(R.drawable.speed_up, buttonWidth, buttonHeight);
        this.mPlasmaTower=helperFactory.bitmapMaker(R.drawable.plasma_turret, buttonWidth, buttonHeight);
        this.mLaserTower=helperFactory.bitmapMaker(R.drawable.laser_turret, buttonWidth, buttonHeight);
        this.mRocketTower=helperFactory.bitmapMaker(R.drawable.rocket_turret, buttonWidth, buttonHeight);
        this.mLevel1SplashScreen=helperFactory.bitmapMaker(R.drawable.level1_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mLevel2SplashScreen=helperFactory.bitmapMaker(R.drawable.level2_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mLevel3SplashScreen=helperFactory.bitmapMaker(R.drawable.level3_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mWonSplashScreen=helperFactory.bitmapMaker(R.drawable.won_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mLostSplashScreen=helperFactory.bitmapMaker(R.drawable.lost_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mPausedSplashScreen=helperFactory.bitmapMaker(R.drawable.paused_splash_screen, mSplashScreenSize, mSplashScreenSize);
    }

    private void drawGraphics(Canvas canvas) //draw the graphics for the buttons
    {
        canvas.drawBitmap(mPauseBitMap, mPauseLocation.x, mPauseLocation.y, null);
        canvas.drawBitmap(mSpeedUpBitMap, mSpeedUpLocation.x, mSpeedUpLocation.y, null);
        canvas.drawBitmap(mPlasmaTower, mPlasmaTowerLocation.x, mPlasmaTowerLocation.y, null);
        canvas.drawBitmap(mLaserTower, mLaserTowerLocation.x, mLaserTowerLocation.y, null);
        canvas.drawBitmap(mRocketTower, mRocketTowerLocation.x, mRocketTowerLocation.y, null);
    }

    public void addTowerInfo(Tower mTower){

        this.towerInfo=new TowerInfo(mTower, size);
        this.alienInfo=null;
        this.placingInfo=null;
    }
    public void addAlienInfo(Alien mAlien){

        this.alienInfo=new AlienInfo(mAlien, size);
        this.towerInfo=null;
        this.placingInfo=null;
    }
    public void addPlacementInfo(Tower.TowerType mTower){
        this.placingInfo=new PlacingInfo(mTower, size);
        this.alienInfo=null;
        this.towerInfo=null;
    }
    public void removeAllInfo(){

        placingInfo=null;
        this.alienInfo=null;
        this.towerInfo=null;
    }

    public boolean onButton(Point point){
        boolean collision=false;
        for(Rect r : controls)
        {
            if (r.contains(point.x, point.y))
                collision=true;
        }
        return collision;
    }
}