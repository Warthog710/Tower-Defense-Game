package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.Random;

public class Enemy extends GameObject implements Alien
{
    private float health, resist;
    private String mInfo;
    private Movable movementStrategy;
    private AlienHealthBar mHealthBar;

    private Enemy(EnemyBuilder builder)
    {
        //Initialize variables
        this.health = builder.health;
        this.resist = builder.resist;
        this.movementStrategy = builder.movementStrategy;
        this.mHealthBar = builder.mHealthbar;
        this.mBitmap = builder.mBitmap;
        this.mInfo=builder.mInfo;
        this.setAttributeSize(builder.attributeSize);


        //Modify variables
        mLocation = new Point();
        mLocation.x = new Random().nextInt(100);
        mLocation.y = (new Random().nextInt(builder.pathHeight - getAttributeSize()) + ((builder.size.y / 2)) - getAttributeSize());
        this.movementStrategy.setLocation(mLocation);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), true);
    }

    public void onHit(float dmg)
    {
        health = health - (dmg * resist);
    }
    public void setResistance(float resist)
    {
        this.resist = resist;
    }

    public void move(){ movementStrategy.move(); }
    public float getHealth() { return this.health; }

    //Return a hitbox for the current location of the drone
    public Rect getHitbox()
    {
        return new Rect(movementStrategy.getLocation().x - (mBitmap.getWidth() / 2),
                movementStrategy.getLocation().y - (mBitmap.getHeight() / 2),
                movementStrategy.getLocation().x + (mBitmap.getWidth() / 2),
                movementStrategy.getLocation().y + (mBitmap.getHeight()) / 2);
    }

    @Override
    public String getInfo() {
        return mInfo;
    }

    public boolean checkCollision(Rect mBase)
    {
        //If the drone collides with the base
        if (getHitbox().intersects(getHitbox(), mBase))
            return true;

        return false;
    }

    //Custom drawing method for enemies
    @Override
    public void draw(Canvas canvas, Paint paint)
    {
        super.draw(canvas, paint);

        //Draw healthbar
        mHealthBar.draw(canvas, paint, this.health, movementStrategy.getLocation());
    }

    //Inline builder class
    public static class EnemyBuilder
    {
        //Necessary variables
        private float health, resist;
        private int attributeSize, pathHeight, speed;
        private Movable movementStrategy;
        private AlienHealthBar mHealthbar;
        private Bitmap mBitmap;
        private Point size;
        private String mInfo;

        //Set spawn location
        public EnemyBuilder setLocation(Point size)
        {
            this.size = size;

            return this;
        }

        //Set height of path. Randomly spawns at a certain height on the path.
        public EnemyBuilder setPathHeight(int pathHeight)
        {
            this.pathHeight = pathHeight;

            return this;
        }

        //Set enemy health
        public EnemyBuilder setHealth(int health)
        {
            this.health = health;

            return this;
        }

        //Set enemy movement speed
        public EnemyBuilder setSpeed(int speed)
        {
            this.speed = speed;

            return this;
        }

        //Set enemy resistance
        public EnemyBuilder setResist(int resist)
        {
            this.resist = resist;

            return this;
        }

        //Set enemy attribute size
        public EnemyBuilder setAttributeSize(int attributeSize)
        {
            this.attributeSize = attributeSize;

            return this;
        }

        //Attach a healthbar. Height defines the height of the healthbar off the enemy.
        public EnemyBuilder attachHealthBar(int height)
        {
            this.mHealthbar = new AlienHealthBar(height, attributeSize, health);

            return this;
        }

        //Attach a movement strategy
        public EnemyBuilder attachMovementStrategy()
        {
            this.movementStrategy = new DroneMovementStrategy(speed);

            return this;
        }

        //Set the enemies Bitmap
        public EnemyBuilder setBitmap(Context context)
        {
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_alien);

            return this;
        }

        public EnemyBuilder setInfo(String info)
        {
            this.mInfo = info;

            return this;
        }

        //Build the enemy!
        public Enemy build()
        {
            return new Enemy(this);
        }
    }
}
