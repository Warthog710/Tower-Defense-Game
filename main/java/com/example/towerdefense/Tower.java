package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Tower extends GameObject
{
    //public TowerData mTowerData; //tower data
    public long lastShot; //time that the last shot was taken
    public int mRange; //range in pixels
    public int mRateOfFire; //shots per second
    public int mCost; //cost of the tower
    public int mDamage; //amount of damage each projectile does
    public Bitmap mProjectileBitmap;
    public Bitmap mOriginalBitMap;
    public String mDescription;
    public String mName;
    public int mUpgradeCost;
    final static int towerSize=100; //size of all the towers
    enum TowerType {PLASMA, LASER, ROCKET} //types of towers

    public abstract void shoot(GameWorld gameWorld); //method that calls the tower to shoot

    public abstract void upgrade(); //upgrades the tower;

    public void setSize(){setAttributeSize(towerSize);} //set the size of the tower

    public boolean contains(Point point){ //check to see if a point exists on the tower
        Rect hitbox = new Rect(mLocation.x-getAttributeSize()/2, mLocation.y-getAttributeSize()/2,
                mLocation.x + getAttributeSize()/2, mLocation.y +getAttributeSize()/2);
        return hitbox.contains(point.x,point.y);
    }

    public boolean inRange(Alien alien) //check to see if an alien is in range
    {
        int x=mLocation.x-alien.getLocation().x;
        int y=mLocation.y-alien.getLocation().y;
        if (Math.sqrt(x*x+y*y)<=mRange)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
