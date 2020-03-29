package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Iterator;

public class PlasmaTower extends Tower
{

    public PlasmaTower(Context context, Point location)
    {
        setSize();
        this.mLocation=location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mTowerData=new TowerData();
        this.mTowerData.mProjectileData=new ProjectileData();
        this.mTowerData.mProjectileData.mDamage=5;
        this.mTowerData.mCost=10;
        this.mTowerData.mRange=600;
        this.mTowerData.mRateOfFire=2;
        this.mTowerData.mProjectileData.mProjectileBitMap=BitmapFactory.decodeResource(context.getResources(), R.drawable.test_bullet);
        this.mTowerData.mProjectileData.mProjectileSpeed=2200;
        this.mTowerData.mProjectileData.mProjectileSize=10;
        lastShot=System.currentTimeMillis();
    }

    @Override
    public void shoot(GameWorld gameWorld)
    {
        System.out.println(System.currentTimeMillis()-lastShot);
        if (System.currentTimeMillis()-lastShot>(1000)){
            lastShot=System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
            while(alienIterator.hasNext()){
                Alien alien=alienIterator.next();
                if(inRange(alien)){
                    gameWorld.mProjectiles.add(new Projectile(mTowerData, mLocation, alien.getLocation() ));
                    alien.onHit(mTowerData.mProjectileData.mDamage);
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
