package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/*
Information about a tower that is being placed
 */

public class PlacingInfo {
    public Rect cancelButton;

    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPadding;
    private String description, stats;

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

        switch(mTowerType) //set up the stats and description based on the tower type
        {
            case PLASMA:
                description="Touch to place a level 1 Plasma Tower";
                stats="Cost: "+Tower.PLASMA_COST + " | " +"Damage: "+5+" | Rate of Fire: "+4+" | Range: "+300;
                break;
            case LASER:
                description="Touch to place a level 1 Laser Tower";
                stats="Cost: "+Tower.LASER_COST + " | " +"Damage: "+15+" | Rate of Fire: "+1+" | Range: "+500;
                break;
            case ROCKET:
                description="Touch to place a level 1 Rocket Tower";
                stats="Cost: "+Tower.ROCKET_COST + " | " +"Damage: "+50+" | Rate of Fire: "+0.5+" | Range: "+500;
                break;
        }
    }

    public void draw(Canvas canvas, Paint paint) { //draw the tower info
        paint.setColor(GameWorld.black); //set color to black
        canvas.drawRect(cancelButton.left, cancelButton.top, cancelButton.right, cancelButton.bottom, paint);

        //draw stats
        paint.setTextSize(mTextFormatting / 1.5f);
        canvas.drawText(description,
                mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18, mScreenHeight - mTextFormatting*2, paint);
        canvas.drawText(stats,
                mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18, mScreenHeight - mTextFormatting, paint);
        // Set the color to white
        paint.setColor(GameWorld.white);
        canvas.drawText("Cancel",
                mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight, paint);
        canvas.drawText("Placement",
                mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight + mTextFormatting, paint);

    }
}