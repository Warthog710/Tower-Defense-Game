package com.example.towerdefense;

import android.graphics.Bitmap;

public class ProjectileData { //the data about the projectile
    public int mDamage; //amount of damage each projectile does
    public int mProjectileSpeed; //speed of projectile
    public Bitmap mProjectileBitMap; //projectile image
    public int mProjectileSize; //size of projectile

    public ProjectileData()
    {

    }

    public void upgradeDamage(){
        this.mDamage=(int) (1.1*this.mDamage);
    } //increase the damage
}
