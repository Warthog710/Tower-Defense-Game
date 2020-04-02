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
    static int PAUSE = 0;
    static int PlasmaTower=1;
    static int white = Color.argb(255,255,255,255);
    static int black = Color.argb(255,0,0,0);
    private Bitmap mPauseBitMap, mPlasmaTower;
    private Point mPauseLocation, mPlasmaTowerLocation;
    private int buttonWidth;
    private int buttonHeight;

    HUD(Point size)
    {
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        prepareControls();
    }

    private void prepareControls()
    {
        int buttonPadding = mScreenWidth / 90;
        Rect pause = new Rect( //pause button
                mScreenWidth - buttonPadding - buttonWidth, buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);
        Rect plasmaTower = new Rect( //tower button
                mScreenWidth - (buttonPadding*2) - (buttonWidth*2),
                buttonPadding,
                mScreenWidth - buttonPadding*2-buttonWidth,
                buttonPadding + buttonHeight);
        controls = new ArrayList<>();
        controls.add(PAUSE, pause);
        controls.add(PlasmaTower, plasmaTower);

        mPauseLocation= new Point(mScreenWidth - buttonPadding - buttonWidth,buttonPadding);
        mPlasmaTowerLocation= new Point(mScreenWidth - (buttonPadding*2) - (buttonWidth*2),buttonPadding);

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
            if (mGameWorld.getLives() < 0)
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

        drawControls(c, p);
        drawGraphics(c);
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
        mPlasmaTower=BitmapFactory.decodeResource(context.getResources(), R.drawable.plasma_turret);
        mPlasmaTower=Bitmap.createScaledBitmap(mPlasmaTower, buttonWidth, buttonHeight, false);
    }

    private void drawGraphics(Canvas canvas) //draw the graphics for the buttons
    {
        canvas.drawBitmap(mPauseBitMap, mPauseLocation.x, mPauseLocation.y, null);
        canvas.drawBitmap(mPlasmaTower, mPlasmaTowerLocation.x, mPlasmaTowerLocation.y, null);
    }
}