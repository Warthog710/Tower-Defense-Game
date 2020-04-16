package com.example.towerdefense;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Iterator;

public class RocketProjectile extends Movable implements Projectile {
    public final int mSpeed=(int)(500/GameEngine.TARGET_FPS), mSize=50;
    public int mDamage;
    private Alien mTarget;
    private Bitmap mOriginalBitmap;
    private Point mOrigin;

    public RocketProjectile(Bitmap mBitMap, Point mLocation, Alien mTarget, int mDamage){
        this.setAttributeSize(mSize);
        this.mLocation=mLocation;
        this.mOrigin=mLocation;
        this.mDamage=mDamage;
        this.mTarget=mTarget;
        this.mBitmap= mBitMap;
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOriginalBitmap=mBitMap;
        this.mOriginalBitmap = Bitmap.createScaledBitmap(mOriginalBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mHeading=new Angle();
        mHeading.setAngle((int)Math.toDegrees(Math.atan2((mTarget.getLocation().y-mLocation.y),(mTarget.getLocation().x-mLocation.x)))+90);
        Matrix matrix = new Matrix();
        matrix.postRotate(mHeading.getAngle());
        mBitmap = Bitmap
                .createBitmap(mBitmap,
                        0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
    }
    @Override
    public void move() {
        if(this.mTarget.getHealth()>0) {
            mHeading.setAngle((int) Math.toDegrees(Math.atan2((mTarget.getLocation().y - mLocation.y), (mTarget.getLocation().x - mLocation.x))) + 90);
            Matrix matrix = new Matrix();
            matrix.postRotate(mHeading.getAngle());
            mBitmap = Bitmap
                    .createBitmap(mOriginalBitmap,
                            0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
        }
        int xSpeed=((int)(Math.cos(Math.toRadians(mHeading.getAngle()-90))*mSpeed));
        int ySpeed=((int)(Math.sin(Math.toRadians(mHeading.getAngle()-90))*mSpeed));
        mLocation= new Point(mLocation.x+xSpeed,mLocation.y+ySpeed);
    }

    @Override
    public boolean remove(GameWorld gameWorld) {
        Rect hitbox = new Rect(mLocation.x-getAttributeSize()/2, mLocation.y-getAttributeSize()/2,
                mLocation.x + getAttributeSize()/2, mLocation.y +getAttributeSize()/2);
        boolean value=false;
        Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
        while(alienIterator.hasNext()) {
            Alien alien = alienIterator.next();
            if (alien.checkCollision(hitbox)) { //see if the projectile hit the alien
                alien.onHit(mDamage);
                value=true;
                break;
            }
        }
        if(mLocation.x>gameWorld.mMap.size.x || mLocation.y>gameWorld.mMap.size.y)
            value=true;
        return value;
    }
}
