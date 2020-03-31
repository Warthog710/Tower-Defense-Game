package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TowerDataFactory {
    public TowerData getTowerData(Tower.TowerType towerType, Context context){
        TowerData towerData=null;
        switch(towerType)
        {
            case PLASMA:
                towerData = new TowerData();
                towerData.mRateOfFire=4;
                towerData.mRange=300;
                towerData.mCost=50;
                towerData.mProjectileData= new ProjectileData();
                towerData.mProjectileData.mProjectileSize=50;
                towerData.mProjectileData.mDamage=5;
                towerData.mProjectileData.mProjectileSpeed=2200;
                towerData.mProjectileData.mProjectileBitMap= BitmapFactory.decodeResource(context.getResources(), R.drawable.test_plasma);
                towerData.bitmapHolder= BitmapFactory.decodeResource(context.getResources(), R.drawable.plasma_turret);
                towerData.bitmapHolder = Bitmap.createScaledBitmap(towerData.bitmapHolder, Tower.towerSize, Tower.towerSize, false);
                break;
        }
        return towerData;
    }
}
