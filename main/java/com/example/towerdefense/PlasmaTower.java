package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.Iterator;

public class PlasmaTower extends Tower
{

    public PlasmaTower(Context context, Point location)
    {
        setSize();
        TowerDataFactory towerDataFactory= new TowerDataFactory();
        this.mLocation=location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plasma_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mTowerData=towerDataFactory.getTowerData(TowerType.PLASMA, context);
        lastShot=System.currentTimeMillis();
    }

    @Override
    public void shoot(GameWorld gameWorld)
    {
        if (System.currentTimeMillis()-lastShot>(1000/mTowerData.mRateOfFire)){
            lastShot=System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
            while(alienIterator.hasNext()){
                Alien alien=alienIterator.next();
                if(inRange(alien)){
                    gameWorld.mProjectiles.add(new Projectile(mTowerData, mLocation, alien.getLocation() ));
                    alien.onHit(mTowerData.mProjectileData.mDamage);
                    int angle=(int)Math.toDegrees(Math.atan2((alien.getLocation().y-mLocation.y),(alien.getLocation().x-mLocation.x)))+90;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    mBitmap = Bitmap
                            .createBitmap(mTowerData.bitmapHolder,
                                    0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
                    break;
                }
            }
        }
    }

    private boolean inRange(Alien alien)
    {
        int x=mLocation.x-alien.getLocation().x;
        int y=mLocation.y-alien.getLocation().y;
        if (Math.sqrt(x*x+y*y)<=mTowerData.mRange)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
