package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TowerDataFactory { //factory for building tower data
    public TowerData getTowerData(Tower.TowerType towerType, Context context){
        TowerData towerData=null;
        switch(towerType)
        {
            case PLASMA: //set up all the data for a plasma tower
                towerData = new TowerData();
                towerData.mRateOfFire=4; //shots per second
                towerData.mRange=300; //range in pixels
                towerData.mCost=50; //cost of the tower
                towerData.mProjectileData= new ProjectileData();
                towerData.mProjectileData.mProjectileSize=50; //size of projectile
                towerData.mProjectileData.mDamage=5; //projectile damage
                towerData.mProjectileData.mProjectileSpeed=2200; //projectile speed (pixels per second)
                towerData.mProjectileData.mProjectileBitMap= BitmapFactory.decodeResource(context.getResources(), R.drawable.test_plasma);
                towerData.bitmapHolder= BitmapFactory.decodeResource(context.getResources(), R.drawable.plasma_turret);
                towerData.bitmapHolder = Bitmap.createScaledBitmap(towerData.bitmapHolder, Tower.towerSize, Tower.towerSize, false);
                break;
            case LASER: //set up all the data for a plasma tower
                towerData = new TowerData();
                towerData.mRateOfFire=1; //shots per second
                towerData.mRange=400; //range in pixels
                towerData.mCost=100; //cost of the tower
                towerData.mProjectileData= new ProjectileData();
                towerData.mProjectileData.mProjectileSize=10; //size of projectile
                towerData.mProjectileData.mDamage=15; //projectile damage
                towerData.mProjectileData.mProjectileSpeed=2200; //projectile speed (pixels per second)
                towerData.mProjectileData.mProjectileBitMap= BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_beam);
                towerData.bitmapHolder= BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_turret);
                towerData.bitmapHolder = Bitmap.createScaledBitmap(towerData.bitmapHolder, Tower.towerSize, Tower.towerSize, false);
                break;
            case ROCKET: //set up all the data for a plasma tower
                towerData = new TowerData();
                towerData.mRateOfFire=2; //shots per second
                towerData.mRange=500; //range in pixels
                towerData.mCost=100; //cost of the tower
                towerData.mProjectileData= new ProjectileData();
                towerData.mProjectileData.mProjectileSize=50; //size of projectile
                towerData.mProjectileData.mDamage=10; //projectile damage
                towerData.mProjectileData.mProjectileSpeed=1500; //projectile speed (pixels per second)
                towerData.mProjectileData.mProjectileBitMap= BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket);
                towerData.bitmapHolder= BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_turret);
                towerData.bitmapHolder = Bitmap.createScaledBitmap(towerData.bitmapHolder, Tower.towerSize, Tower.towerSize, false);
                break;
        }
        return towerData;
    }
}
