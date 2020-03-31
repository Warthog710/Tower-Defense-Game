package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Tower extends GameObject
{
    public TowerData mTowerData;
    public long lastShot;
    final static int towerSize=100;
    Bitmap bitmapHolder;

    public abstract void shoot(GameWorld gameWorld);
    public void setSize(){setAttributeSize(towerSize);}
    public boolean contains(Point point){
        Rect hitbox = new Rect(mLocation.x, mLocation.y,
                mLocation.x + getAttributeSize(), mLocation.y + getAttributeSize());
        return hitbox.contains(point.x,point.y);
    }
}
