package com.example.towerdefense;

import android.graphics.Bitmap;

public class TowerData {
    public int mRange; //range in pixels
    public int mRateOfFire; //shots per second
    public int mCost; //cost of the tower
    public ProjectileData mProjectileData;

    public TowerData(){

    }
    public void typePlasma(){
        this.mProjectileData=new ProjectileData();
        this.mProjectileData.mDamage=5;
        this.mCost=10;
        this.mRange=300;
        this.mRateOfFire=4;
    }

    public void upgradeRange(){
        this.mRange=(int)(1.1*this.mRange);
    }
    public void upgradeRateOfFire(){
        this.mRateOfFire=(int)(1.1*this.mRateOfFire);
    }
    public int getRange(){ return mRange;}
    public int getRateOfFire(){return mRateOfFire;}
    public int getCost(){ return mCost;}
}
