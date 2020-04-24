package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class PlacingInfo {
    private Tower mTower;
    private Circle towerRange;
    public Rect cancelButton;

    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPadding;
    static int white = Color.argb(255, 255, 255, 255);
    static int black = Color.argb(255, 0, 0, 0);
    private String description;
    private int cost;

    public PlacingInfo(Tower.TowerType mTowerType, Point size) {
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        buttonPadding = mScreenWidth / 90;
        cancelButton = new Rect( //tower button
                mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonPadding);
        switch(mTowerType)
        {
            case PLASMA: //make a plasma tower
                description="Placing a level 1 Plasma Tower";
                cost=50;
                break;
            case LASER: //make a plasma tower
                description="Placing a level 1 Laser Tower";
                cost=100;
                break;
            case ROCKET: //make a plasma tower
                description="Placing a level 1 Rocket Tower";
                cost=100;
                break;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(black); //set color to black
        canvas.drawRect(cancelButton.left, cancelButton.top, cancelButton.right, cancelButton.bottom, paint);

        //draw stats
        paint.setTextSize(mTextFormatting / 1.5f);
        canvas.drawText(description,
                mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18, mScreenHeight - mTextFormatting, paint);
        // Set the color to white
        paint.setColor(white);
        canvas.drawText("Cancel",
                mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight, paint);
        canvas.drawText("Placement",
                mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight + mTextFormatting, paint);

    }

    public int cancel() {
        return cost;
    }
}