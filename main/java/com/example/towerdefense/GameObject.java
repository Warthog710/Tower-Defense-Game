package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject
{
    //Class variables
    Point mLocation;
    private int mAttributeSize;
    Bitmap mBitmap;
    private boolean sizeSet=false;

    //Default draw method
    void draw (Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mBitmap, mLocation.x, mLocation.y, null);
    }

    //Getters
    public Point getLocation() { return mLocation; }
    public int getAttributeSize(){ return mAttributeSize; }
    public void setAttributeSize(int size){
        if(!sizeSet){
            mAttributeSize=size;
            sizeSet=true;
        }
    }

    //Setters
    public void setLocation(Point mLocation){ this.mLocation = mLocation; }
}
