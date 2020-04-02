package com.example.towerdefense;

import android.graphics.Bitmap;
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
    private AlienHealthBar mHealthBar;

    //Drone constructor.
    public Drone(Context context, Point size, Movable strategy, int pathHeight, int attributeSize)
    {
        this.setAttributeSize(attributeSize);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_alien);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), true);
        mLocation = new Point();
        mLocation.x = new Random().nextInt(100);
        mLocation.y = (new Random().nextInt(pathHeight - mBitmap.getHeight()) + ((size.y / 2) - (pathHeight / 2)));
        this.movementStrategy = strategy;
        this.movementStrategy.setLocation(mLocation);
        this.mHealthBar = new AlienHealthBar(10, getAttributeSize(), this.health);
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

        //Draw healthbar
        mHealthBar.draw(canvas, paint, this.health, movementStrategy.getLocation());
    }

    public void move(){ movementStrategy.move(); }

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
        return new Rect(movementStrategy.getLocation().x,
                movementStrategy.getLocation().y,
                movementStrategy.getLocation().x + mBitmap.getWidth(),
                movementStrategy.getLocation().y + mBitmap.getHeight());
    }
}
