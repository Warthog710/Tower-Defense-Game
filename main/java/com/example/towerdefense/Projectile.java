package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;

public class Projectile extends Movable
{
    Point mDestination; //where the projectile is going to
    Point mOrigin; //where the projectile came from
    int mDamage; //amount of damage

    public Projectile(TowerData towerData, Point mLocation, Point mDestination)
    {
        this.mDamage=towerData.mProjectileData.mDamage; //set damage
        this.mDestination=mDestination;
        this.mLocation=mLocation;
        this.setAttributeSize(towerData.mProjectileData.mProjectileSize);
        this.mBitmap=towerData.mProjectileData.mProjectileBitMap;
        this.mBitmap= Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOrigin=mLocation;
        this.mHeading=new Angle();
        mHeading.setAngle((int)Math.toDegrees(Math.atan2((mDestination.y-mLocation.y),(mDestination.x-mLocation.x)))+90);
        Matrix matrix = new Matrix();
        matrix.postRotate(mHeading.getAngle());
        mBitmap = Bitmap
                .createBitmap(mBitmap,
                        0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
        this.mSpeed=(int)(towerData.mProjectileData.mProjectileSpeed/GameEngine.TARGET_FPS);
    }

    @Override
    public void move() //move to projectile
    {
        int xSpeed=((int)(Math.cos(Math.toRadians(mHeading.getAngle()-90))*mSpeed));
        int ySpeed=((int)(Math.sin(Math.toRadians(mHeading.getAngle()-90))*mSpeed));
        mLocation= new Point(mLocation.x+xSpeed,mLocation.y+ySpeed);

    }

    public boolean collision(GameWorld gameWorld) //check if the projectile has moved past the destination (more added later)
    {
        if(Math.signum(mDestination.x-mOrigin.x)!=Math.signum(mDestination.x-mLocation.x) ||
                Math.signum(mDestination.y-mOrigin.y)!=Math.signum(mDestination.y-mLocation.y))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
