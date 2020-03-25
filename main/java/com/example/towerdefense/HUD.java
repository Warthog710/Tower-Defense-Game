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
class HUD {
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


    HUD(Point size){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        prepareControls();
    }
    private void prepareControls(){
        int buttonPadding = mScreenWidth / 90;
        Rect pause = new Rect(
                mScreenWidth - buttonPadding - buttonWidth, buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);
        Rect plasmaTower = new Rect(
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
    void draw(Canvas c, Paint p, GameWorld mGameWorld){
        // Draw the HUD
        p.setColor(black);
        p.setTextSize(mTextFormatting);

        c.drawText("Lives: " + mGameWorld.getLives(),
                mTextFormatting, mTextFormatting * 2,p);
        c.drawText("Cash: " + mGameWorld.getCash(),
                mTextFormatting*6, mTextFormatting * 2,p);

        if(mGameWorld.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PRESS PLAY",
                    mScreenWidth /4, mScreenHeight /2 ,p);
        }
        if(mGameWorld.getPaused() && !mGameWorld.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED",
                    mScreenWidth /3, mScreenHeight /2 ,p);
        }
        drawControls(c, p);
        drawGraphics(c,p);
    }
    private void drawControls(Canvas c, Paint p){
        p.setColor(black);
        for(Rect r : controls){
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }
        // Set the colors back
        p.setColor(white);
    }
    ArrayList<Rect> getControls(){
        return controls;
    }
    public void setGraphics(Context context){
        mPauseBitMap=BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        mPauseBitMap=Bitmap.createScaledBitmap(mPauseBitMap, buttonWidth, buttonHeight, false);
        mPlasmaTower=BitmapFactory.decodeResource(context.getResources(), R.drawable.test_turret);
        mPlasmaTower=Bitmap.createScaledBitmap(mPlasmaTower, buttonWidth, buttonHeight, false);
    }
    private void drawGraphics(Canvas canvas, Paint p){
        canvas.drawBitmap(mPauseBitMap, mPauseLocation.x, mPauseLocation.y, null);
        canvas.drawBitmap(mPlasmaTower, mPlasmaTowerLocation.x, mPlasmaTowerLocation.y, null);

    }
}