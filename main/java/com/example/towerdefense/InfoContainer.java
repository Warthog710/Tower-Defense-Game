package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
/*
Container to hold information about towers, aliens, etc
 */

public class InfoContainer {
    private Tower mTower;
    private Alien mAlien;
    private Circle towerRange;
    public Rect upgradeButton;
    private enum state{TOWER, PLACING, ALIEN}
    private state currentState;
    private String line1, line2, line3, buttonLine1, buttonLine2;
    private boolean hideInfo;

    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPadding;

    public InfoContainer(Point size){ //create the info container and add all the size
        this.hideInfo=true;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        buttonPadding = mScreenWidth / 90;
        upgradeButton = new Rect( //tower button
                mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight-buttonHeight-buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight-buttonPadding);
    }
    public void setInfo(Tower tower){ //set the info if the data is a tower
        this.hideInfo=false;
        this.mTower=tower;
        currentState=state.TOWER;
        line1=mTower.mName;
        line2=mTower.mDescription;
        line3="Damage: "+mTower.mDamage+" | Rate of Fire: "+mTower.mRateOfFire+" | Range: "+mTower.mRange;
        buttonLine1="Upgrade";
        buttonLine2="$"+mTower.mUpgradeCost;
        towerRange= new Circle(tower.getLocation(),tower.mRange);
    }
    public void setInfo(Tower.TowerType towerType){ //set the info if the data is a tower type
        this.hideInfo=false;
        currentState=state.PLACING;
        buttonLine1="Cancel";
        buttonLine2="Placement";

        switch (towerType){
            case LASER:
                line1="Touch to place a level 1 laser tower";
                line2="Laser towers fire at medium speed with decent damage";
                line3="Cost "+100+" | Damage: "+15+" | Rate of Fire: "+1+" | Range: "+500;
                break;
            case PLASMA:
                line1="Touch to place a level 1 plasma tower";
                line2="Plasma towers fire quickly but have low damage";
                line3="Cost "+50+" | Damage: "+5+" | Rate of Fire: "+4+" | Range: "+300;
                break;
            case ROCKET:
                line1="Touch to place a level 1 rocket tower";
                line2="Rocket towers fire slowly but deal huge damage";
                line3="Cost "+200+" | Damage: "+50+" | Rate of Fire: "+0.5+" | Range: "+500;
                break;

        }
    }
    public void setInfo(Alien alien){ //set the info if the data is alien
        this.hideInfo=false;
        this.mAlien=alien;
        currentState=state.ALIEN;
        line1="";
        line2=mAlien.getInfo();
        line3=mAlien.getResistance();
        buttonLine1="";
        buttonLine2="";

    }

    public void draw(Canvas canvas, Paint paint){ //draw the tower info
        if (!hideInfo) {
            if (currentState == state.TOWER) {
                towerRange.draw(canvas, paint); //draw range
                line3 = "Damage: " + mTower.mDamage + " | Rate of Fire: " + mTower.mRateOfFire + " | Range: " + mTower.mRange; //update line 3
                buttonLine2 = "$" + mTower.mUpgradeCost; //update cost of upgrade
                towerRange.draw(canvas, paint); //draw range
            }
            paint.setColor(GameWorld.black); //set color to black
            if (currentState != state.ALIEN) { //if the info is not a alien, show the button
                canvas.drawRect(upgradeButton.left, upgradeButton.top, upgradeButton.right, upgradeButton.bottom, paint);
            }

            //draw stats
            paint.setTextSize(mTextFormatting / 1.5f);
            canvas.drawText(line1,
                    mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18, mScreenHeight - mTextFormatting * 3, paint);
            canvas.drawText(line2,
                    mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18, mScreenHeight - mTextFormatting * 2, paint);
            canvas.drawText(line3,
                    mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18, mScreenHeight - mTextFormatting, paint);
            // Set the color to white
            paint.setColor(GameWorld.white);
            canvas.drawText(buttonLine1,
                    mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight, paint);
            canvas.drawText(buttonLine2,
                    mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight + mTextFormatting, paint);
        }

    }

    public void buttonClick(){ //button has been clicked
        if(currentState==state.TOWER){ //tower is showing so upgrade button was hit
            towerUpgrade();
        }else{ //cancel placement button so hide state
            hideInfo();

        }

    }

    public void hideInfo(){ //hide the info
        hideInfo=true;
    }

    private void towerUpgrade(){ //upgrade the tower
        this.mTower.upgrade();
        this.towerRange= new Circle(mTower.mLocation, mTower.mRange);
        this.mTower.mUpgradeCost=(int)(this.mTower.mUpgradeCost*1.3);
    }
}
