package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Projectile extends MovableClass {
    Point mDestination;
    Point mOrigin;
    int mDamage;

    public Projectile(TowerData towerData, Point mLocation, Point mDestination){
        this.mDamage=towerData.mProjectileData.mDamage;
        this.mDestination=mDestination;
        this.mLocation=mLocation;
        this.setAttributeSize(towerData.mProjectileData.mProjectileSize);
        this.mBitmap=towerData.mProjectileData.mProjectileBitMap;
        this.mBitmap= Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOrigin=mLocation;
        this.mHeading=new Angle();
        mHeading.setAngle((int)Math.toDegrees(Math.atan2((mDestination.y-mLocation.y),(mDestination.x-mLocation.x))));
        this.mSpeed=(int)(towerData.mProjectileData.mProjectileSpeed/GameEngine.TARGET_FPS);
    }

    @Override
    public void move() {
        int xSpeed=((int)(Math.cos(Math.toRadians(mHeading.getAngle()))*mSpeed));
        int ySpeed=((int)(Math.sin(Math.toRadians(mHeading.getAngle()))*mSpeed));
        mLocation= new Point(mLocation.x+xSpeed,mLocation.y+ySpeed);

    }

    public boolean collision(GameWorld gameWorld){
        if(Math.signum(mDestination.x-mOrigin.x)!=Math.signum(mDestination.x-mLocation.x) ||
                Math.signum(mDestination.y-mOrigin.y)!=Math.signum(mDestination.y-mLocation.y)){
            return true;
        }else{
            return false;
        }
    }
}
