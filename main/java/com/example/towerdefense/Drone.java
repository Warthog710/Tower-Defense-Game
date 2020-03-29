package com.example.towerdefense;

import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.Random;

public class Drone extends GameObject implements Alien
{
    private float health = 10;
    private float resist = 1;
    private Movable movementStrategy;

    //Drone constructor.
    public Drone(Context context, Point size, Movable strategy, int pathHeight)
    {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_alien);
        mLocation = new Point();
        mLocation.x = new Random().nextInt(100);
        mLocation.y = (new Random().nextInt(pathHeight - mBitmap.getHeight()) + ((size.y / 2) - (pathHeight / 2)));
        this.movementStrategy = strategy;
    }

    public void onHit(float dmg)
    {
        health = health - (dmg * resist);
    }

    public void setResistance(float resist)
    {
        this.resist = resist;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        super.draw(canvas, paint);
    }

    public void move(){ mLocation = movementStrategy.move(mLocation); }

    public float getHealth() { return this.health; }

    public boolean checkCollision(Rect mBase)
    {
        //If the drone collides with the base
        if (getHitbox().intersects(getHitbox(), mBase))
            return true;

        return false;
    }

    //Return a hitbox for the current location of the drone
    public Rect getHitbox()
    {
        return new Rect(mLocation.x, mLocation.y, mLocation.x + mBitmap.getWidth(), mLocation.y + mBitmap.getHeight());
    }
}
