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
        TowerDataFactory towerDataFactory= new TowerDataFactory();
        this.mLocation=location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mTowerData=towerDataFactory.getTowerData(TowerType.LASER, context);
        lastShot=System.currentTimeMillis();
    }

    @Override
    public void shoot(GameWorld gameWorld) //shoot at the first enemy it can
    {
        if (System.currentTimeMillis()-lastShot>(1000/mTowerData.mRateOfFire)){ //can the tower fire?
            lastShot=System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
            while(alienIterator.hasNext()){
                Alien alien=alienIterator.next();
                if(inRange(alien)){ //see which enemies are in range
                    gameWorld.mLasers.add(new Laser(mLocation, alien.getLocation() )); //create projectile
                    alien.onHit(mTowerData.mProjectileData.mDamage); //calculate damage
                    int angle=(int)Math.toDegrees(Math.atan2((alien.getLocation().y-mLocation.y),(alien.getLocation().x-mLocation.x)))+90;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    mBitmap = Bitmap //rotate bitmap to face enemy
                            .createBitmap(mTowerData.bitmapHolder,
                                    0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
                    break;
                }
            }
        }
    }
}
