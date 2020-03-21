package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject
{
    //Class variables
    Point mLocation;
    int attributeSize;
    Bitmap mBitmap;

    //Default draw method
    void draw (Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mBitmap, mLocation.x, mLocation.y, null);
    }

    //Getters
    public Point getLocation() { return mLocation; }
    public int getAttributeSize(){ return attributeSize; }

    //Setters
    public void setLocation(Point mLocation){ this.mLocation = mLocation; }
}
