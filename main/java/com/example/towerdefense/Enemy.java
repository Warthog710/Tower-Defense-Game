package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends GameObject implements Alien
{
    private float health, resist;
    private String mInfo;
    private EnemyMovable movementStrategy;
    private AlienHealthBar mHealthBar;

    //Private class constructor
    private Enemy(EnemyBuilder builder)
    {
        //Initialize variables
        this.health = builder.health;
        this.resist = builder.resist;
        this.movementStrategy = builder.movementStrategy;
        this.mHealthBar = builder.mHealthbar;
        this.mBitmap = builder.mBitmap;
        this.mInfo=builder.mInfo;
        this.mLocation = builder.mLocation;
        this.setAttributeSize(builder.attributeSize);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), true);
    }

    //Call onHit to deal dmg
    public void onHit(float dmg)
    {
        health = health - (dmg * resist);
        if (health<0)
            health=0;
    }

    //Calls move() in the movement strategy to move the enemy along the path
    public void move(ArrayList<PathPoints> path){ movementStrategy.move(path); }

    //Return a hitbox for the current location of the drone
    public Rect getHitbox()
    {
        return new Rect(movementStrategy.getLocation().x - (mBitmap.getWidth() / 2),
                movementStrategy.getLocation().y - (mBitmap.getHeight() / 2),
                movementStrategy.getLocation().x + (mBitmap.getWidth() / 2),
                movementStrategy.getLocation().y + (mBitmap.getHeight()) / 2);
    }

    //Getter methods.
    @Override
    public Point getLocation() { return movementStrategy.getLocation(); }
    @Override
    public String getInfo() {
        return mInfo;
    }
    public float getHealth() { return this.health; }

    //Set method
    public void setResistance(float resist)
    {
        this.resist = resist;
    }

    //Call to instantly kill the enemy
    @Override
    public void kill() {
        health=0;
    }

    @Override
    public String getResistance() {
        return "Resistance | Plasma: "+resist+" | Rockets: "+resist+" | Lasers: "+resist;
    }

    //Call to check collision with another hitbox.
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
        //Update heading...
        //Matrix matrix = new Matrix();
        //matrix.postRotate(movementStrategy.getAngle());
        //mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, getAttributeSize(), getAttributeSize(), matrix, true);

        //Draw enemy
        canvas.drawBitmap(mBitmap, movementStrategy.getLocation().x - getAttributeSize()/2, movementStrategy.getLocation().y - getAttributeSize()/2, null);

        //Draw healthbar
        mHealthBar.draw(canvas, paint, this.health, movementStrategy.getLocation());
    }

    //Inline builder class
    public static class EnemyBuilder
    {
        //Necessary variables
        private float health, resist;
        private int attributeSize, pathHeight, speed;
        private EnemyMovable movementStrategy;
        private AlienHealthBar mHealthbar;
        private Bitmap mBitmap;
        private Point mLocation;
        private String mInfo;

        //Set spawn location
        public EnemyBuilder setLocation(PathPoints start)
        {
            this.mLocation = start.getPath();

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
        public EnemyBuilder attachMovementStrategy(String type)
        {
            switch (type)
            {
                case "soldier":
                    this.movementStrategy = new SoldierMovementStrategy(mLocation);
                    break;

                case "behemoth":
                    this.movementStrategy = new BehemothMovementStrategy(mLocation);
                    break;

                default:
                    this.movementStrategy = new DroneMovementStrategy(mLocation);
                    break;

            }
            return this;
        }

        //Set the enemies Bitmap
        public EnemyBuilder setBitmap(Context context, String type)
        {
            switch (type)
            {
                case "soldier":
                    mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier);
                    break;

                case "behemoth":
                    mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beehemoth);
                    break;

                 default:
                     mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.drone);
                     break;

            }
            return this;
        }

        //Set info string
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
