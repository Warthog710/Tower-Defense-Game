package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Laser
{
    //In MS
    final static long TIME=200;

    private long startingTime;
    private Point mLocation;
    private Point mDestination;

    static int white = Color.argb(255,255,255,255);
    static int red = Color.argb(255,255,0,0);
    static int orange = Color.argb(150,255,102,0);

    public Laser(Point mLocation, Point mDestination)
    {
        this.mLocation=mLocation;
        this.mDestination=mDestination;
        this.startingTime=System.currentTimeMillis();

    }

    public boolean done(){
        return (System.currentTimeMillis()>=startingTime+TIME);
    }

    void draw (Canvas canvas, Paint paint)
    {
        paint.setColor(red);
        paint.setStrokeWidth(10);
        canvas.drawLine(mLocation.x, mLocation.y, mDestination.x, mDestination.y, paint);
        paint.setColor(orange);
        paint.setStrokeWidth(3);
        canvas.drawLine(mLocation.x, mLocation.y, mDestination.x, mDestination.y, paint);
        paint.setColor(white);
    }

}
