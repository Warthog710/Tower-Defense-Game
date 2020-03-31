package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.Rect;

public abstract class Tower extends GameObject
{
    public TowerData mTowerData; //tower data
    public long lastShot; //time that the last shot was taken
    final static int towerSize=100; //size of all the towers
    enum TowerType {PLASMA} //types of towers

    public abstract void shoot(GameWorld gameWorld); //method that calls the tower to shoot

    public void setSize(){setAttributeSize(towerSize);} //set the size of the tower

    public boolean contains(Point point){ //check to see if a point exists on the tower
        Rect hitbox = new Rect(mLocation.x, mLocation.y,
                mLocation.x + getAttributeSize(), mLocation.y + getAttributeSize());
        return hitbox.contains(point.x,point.y);
    }
}
