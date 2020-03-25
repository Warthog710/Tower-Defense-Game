package com.example.towerdefense;

public class TowerData {
    public int mRange; //range in pixels
    public int mRateOfFire; //shots per second
    public int mCost; //cost of the tower
    public int mDamage; //ammount of damage each projectile does

    public void upgradeRange(){
        this.mRange=(int)(1.1*this.mRange);
    }
    public void upgradeRateOfFire(){
        this.mRateOfFire=(int)(1.1*this.mRateOfFire);
    }
    public void upgradeDamage(){
        this.mDamage=(int) (1.1*this.mDamage);
    }
    public int getRange(){ return mRange;}
    public int getRateOfFire(){return mRateOfFire;}
    public int getCost(){ return mCost;}
}
