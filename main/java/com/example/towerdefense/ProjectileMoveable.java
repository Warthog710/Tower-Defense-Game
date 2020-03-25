package com.example.towerdefense;

import android.graphics.Point;

public class ProjectileMoveable extends Projectile implements Movable {
    Point mDestination;
    Point mOrigin;
    int mDamage;
    int mSpeed;
    private float xSpeed;
    private float ySpeed;

    public ProjectileMoveable(Projectile projectile, Point mLocation, Point mDestination){
        this.mDamage=projectile.mDamage;
        this.mSpeed=projectile.mSpeed;
        this.mDestination=mDestination;
        this.mLocation=mLocation;
        this.mBitmap=projectile.mBitmap;
        this.mOrigin=mLocation;
        double angle=(Math.atan2((mDestination.y-mLocation.y),(mDestination.x-mLocation.x)));
        float tempSpeed=mSpeed/GameEngine.TARGET_FPS;
        xSpeed=(float)(Math.cos(angle)*tempSpeed);
        ySpeed=(float)(Math.sin(angle)*tempSpeed);
        System.out.println("angle: "+angle);
        System.out.println("cos: "+Math.cos(angle));
        System.out.println(mLocation.y-mDestination.y);
        System.out.println(mLocation.x-mDestination.x);
    }

    @Override
    public void move() {
        mLocation= new Point(mLocation.x+(int)(xSpeed),mLocation.y+(int)(ySpeed));

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
