package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Iterator;

public class PlasmaTower extends Tower {

    public PlasmaTower(Context context, Point location){
        setSize();
        this.mLocation=location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mToweData.mDamage=5;
        this.mToweData.mCost=10;
        this.mToweData.mRange=600;
        this.mToweData.mRateOfFire=2;
        lastShot=System.currentTimeMillis();
    }
    @Override
    public void shoot(GameWorld gameWorld) {
        System.out.println(System.currentTimeMillis()-lastShot);
        if (System.currentTimeMillis()-lastShot>(1000)){
            lastShot=System.currentTimeMillis();
            Iterator<AlienEnemy> alienIterator = gameWorld.mAliens.iterator();
            while(alienIterator.hasNext()){
                AlienEnemy alien=alienIterator.next();
                System.out.println("inShoot");
                if(inRange(alien)){
                    System.out.println("shooting");
                    gameWorld.mProjectiles.add(new ProjectileMoveable(mToweData, mLocation, alien.alien.getLocation() ));

                }
            }
        }



    }
    private boolean inRange(AlienEnemy alien){
        int x=mLocation.x-alien.alien.getLocation().x;
        int y=mLocation.y-alien.alien.getLocation().y;
        if (Math.sqrt(x*x+y*y)<=mRange){
            return true;
        }else{
            return false;
        }

    }

}
