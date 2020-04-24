package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    static int PAUSE = 0;
    static int SPEEDUP = 1;
    static int PlasmaTower=2;
    static int LaserTower=3;
    static int RocketTower=4;
    static int white = Color.argb(255,255,255,255);
    static int black = Color.argb(255,0,0,0);
    private Bitmap mPauseBitMap, mPlasmaTower, mLaserTower, mRocketTower, mSpeedUPBitMap;
    private Point mPauseLocation, mPlasmaTowerLocation, mLaserTowerLocation, mRocketTowerLocation, mSpeedUpLocation;
    private int buttonWidth;
    private int buttonHeight;
    private Point size;

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


        mPauseLocation= new Point(mScreenWidth - buttonPadding - buttonWidth,buttonPadding);
        mSpeedUpLocation= new Point(mScreenWidth - (buttonPadding*2) - (buttonWidth*2),buttonPadding);
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
                p.setTextSize(mTextFormatting * 5);
                c.drawText("YOU LOST",
                        mScreenWidth /4, mScreenHeight /2 ,p);
            }
            //If gameWon is true you must've won
            else if (mGameWorld.getmGameWon())
            {
                p.setTextSize(mTextFormatting * 5);
                c.drawText("YOU WON",
                        mScreenWidth /4, mScreenHeight /2 ,p);
            }
            //Must be a new game...
            else
            {
                p.setTextSize(mTextFormatting * 5);
                c.drawText("PRESS PLAY",
                        mScreenWidth /4, mScreenHeight /2 ,p);
            }
        }

        if(mGameWorld.getPaused() && !mGameWorld.getGameOver()) //if the game is paused
        {
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED",
                    mScreenWidth /3, mScreenHeight /2 ,p);
        }

        drawControls(c, p); //draw the buttons
        drawGraphics(c); //draw the graphics over the buttons

        if (towerInfo !=null){
            towerInfo.draw(c,p);
        }
        if (alienInfo !=null){
            alienInfo.draw(c,p);
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
        mPauseBitMap=BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        mPauseBitMap=Bitmap.createScaledBitmap(mPauseBitMap, buttonWidth, buttonHeight, false);
        mSpeedUPBitMap=BitmapFactory.decodeResource(context.getResources(), R.drawable.speed_up);
        mSpeedUPBitMap=Bitmap.createScaledBitmap(mSpeedUPBitMap, buttonWidth, buttonHeight, false);
        mPlasmaTower=BitmapFactory.decodeResource(context.getResources(), R.drawable.plasma_turret);
        mPlasmaTower=Bitmap.createScaledBitmap(mPlasmaTower, buttonWidth, buttonHeight, false);
        mLaserTower=BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_turret);
        mLaserTower=Bitmap.createScaledBitmap(mLaserTower, buttonWidth, buttonHeight, false);
        mRocketTower=BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_turret);
        mRocketTower=Bitmap.createScaledBitmap(mRocketTower, buttonWidth, buttonHeight, false);
    }

    private void drawGraphics(Canvas canvas) //draw the graphics for the buttons
    {
        canvas.drawBitmap(mPauseBitMap, mPauseLocation.x, mPauseLocation.y, null);
        canvas.drawBitmap(mSpeedUPBitMap, mSpeedUpLocation.x, mSpeedUpLocation.y, null);
        canvas.drawBitmap(mPlasmaTower, mPlasmaTowerLocation.x, mPlasmaTowerLocation.y, null);
        canvas.drawBitmap(mLaserTower, mLaserTowerLocation.x, mLaserTowerLocation.y, null);
        canvas.drawBitmap(mRocketTower, mRocketTowerLocation.x, mRocketTowerLocation.y, null);
    }

    public void addTowerInfo(Tower mTower){
        this.towerInfo=new TowerInfo(mTower, size);
    }
    public void removeTowerInfo(){
        towerInfo=null;
    }
    public void addAlienInfo(Alien mAlien){
        this.alienInfo=new AlienInfo(mAlien, size);
    }
    public void removeAlienInfo(){
        alienInfo=null;
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