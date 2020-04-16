package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.Iterator;

public class LaserTower extends Tower {
    public LaserTower(Context context, Point location)
    {
        setSize(); //set the size
        this.mLocation=location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOriginalBitMap=this.mBitmap;
        this.mProjectileBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_image);
        lastShot=System.currentTimeMillis();

        this.mDamage=20;
        this.mCost=100;
        this.mRateOfFire=1;
        this.mRange=500;
    }

    @Override
    public void shoot(GameWorld gameWorld) //shoot at the first enemy it can
    {
        if (System.currentTimeMillis()-lastShot>(1000/mRateOfFire)){ //can the tower fire?
            lastShot=System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
            while(alienIterator.hasNext()){
                Alien alien=alienIterator.next();
                if(inRange(alien)){ //see which enemies are in range
                    gameWorld.mProjectiles.add(new LaserProjectile(mLocation, alien)); //create projectile
                    alien.onHit(mDamage); //calculate damage
                    int angle=(int)Math.toDegrees(Math.atan2((alien.getLocation().y-mLocation.y),(alien.getLocation().x-mLocation.x)))+90;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    mBitmap = Bitmap //rotate bitmap to face enemy
                            .createBitmap(mOriginalBitMap,
                                    0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
                    break;
                }
            }
        }
    }
}
