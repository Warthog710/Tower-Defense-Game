package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
/*
contains information about a towers
used to display tower data when a tower is selected
 */

public class TowerInfo {
    private Tower mTower;
    private Circle towerRange;
    public Rect upgradeButton;

    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPadding;

    public TowerInfo(Tower mTower, Point size){ //create the tower info
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        buttonPadding = mScreenWidth / 90;
        this.mTower=mTower;
        this.towerRange= new Circle(mTower.mLocation, mTower.mRange);
        upgradeButton = new Rect( //tower button
                mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight-buttonHeight-buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight-buttonPadding);
    }
    public void draw(Canvas canvas, Paint paint){ //draw the tower info
        towerRange.draw(canvas,paint); //draw range

        paint.setColor(GameWorld.black); //set color to black
        canvas.drawRect(upgradeButton.left, upgradeButton.top, upgradeButton.right, upgradeButton.bottom, paint);

        //draw stats
        paint.setTextSize(mTextFormatting/1.5f);
        canvas.drawText("Damage: "+mTower.mDamage+" | Rate of Fire: "+mTower.mRateOfFire+" | Range: "+mTower.mRange,
                mScreenWidth- buttonPadding - buttonWidth-mTextFormatting*18, mScreenHeight-mTextFormatting,paint);
        canvas.drawText(mTower.mDescription,
                mScreenWidth- buttonPadding - buttonWidth-mTextFormatting*18, mScreenHeight-mTextFormatting*2,paint);
        canvas.drawText(mTower.mName,
                mScreenWidth- buttonPadding - buttonWidth-mTextFormatting*18, mScreenHeight-mTextFormatting*3,paint);
        // Set the color to white
        paint.setColor(GameWorld.white);
        canvas.drawText("Upgrade",
                mScreenWidth- buttonPadding - buttonWidth, mScreenHeight-buttonHeight,paint);
        canvas.drawText("$"+mTower.mUpgradeCost,
                mScreenWidth- buttonPadding - buttonWidth, mScreenHeight-buttonHeight+mTextFormatting,paint);

    }

    public void upgrade(){ //upgrade button has been selected
        this.mTower.upgrade();
        this.towerRange= new Circle(mTower.mLocation, mTower.mRange);
        this.mTower.mUpgradeCost=(int)(this.mTower.mUpgradeCost*1.3);
    }

    public int upgradeCost(){ //current cost to upgrade the tower
        return mTower.mUpgradeCost;
    }
}
