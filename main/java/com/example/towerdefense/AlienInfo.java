package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class AlienInfo {
    Alien mAlien;
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPadding;
    static int white = Color.argb(255,255,255,255);
    static int black = Color.argb(255,0,0,0);
    public AlienInfo(Alien mAlien, Point size){
        this.mAlien=mAlien;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        buttonPadding = mScreenWidth / 90;

    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(black); //set color to black
        //draw alien info
        paint.setTextSize(mTextFormatting/1.5f);
        canvas.drawText(mAlien.getInfo(),
                mScreenWidth- buttonPadding - buttonWidth-mTextFormatting*18, mScreenHeight-mTextFormatting,paint);

    }
}
