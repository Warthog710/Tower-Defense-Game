package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
/*
The laser projectile

 */

public class LaserProjectile implements Projectile
{
    final static long TIME=200;

    private long startingTime;
    private Point mLocation;
    private Point mDestination;


    public LaserProjectile(Point mLocation, Alien mTarget) //create laser beam
    {
        this.mLocation=mLocation;
        this.mDestination=mTarget.getLocation();
        this.startingTime=System.currentTimeMillis();
    }

    @Override
    public void move() //dont move
    {
        //Do nothing
    }

    @Override
    public boolean remove(GameWorld gameWorld) //check if the beam has timed out
    {
        return (System.currentTimeMillis()>=startingTime+TIME);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) //draw the beam
    {
        paint.setColor(GameWorld.red);
        paint.setStrokeWidth(10);
        canvas.drawLine(mLocation.x, mLocation.y, mDestination.x, mDestination.y, paint);
        paint.setColor(GameWorld.orange);
        paint.setStrokeWidth(3);
        canvas.drawLine(mLocation.x, mLocation.y, mDestination.x, mDestination.y, paint);
        paint.setColor(GameWorld.white);
    }
}
