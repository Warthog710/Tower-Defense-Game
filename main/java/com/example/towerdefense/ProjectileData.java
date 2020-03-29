package com.example.towerdefense;

import android.graphics.Bitmap;

public class ProjectileData {
    public int mDamage; //amount of damage each projectile does
    public int mProjectileSpeed; //speed of projectile
    public Bitmap mProjectileBitMap;
    public int mProjectileSize;

    public ProjectileData()
    {

    }

    public void upgradeDamage(){
        this.mDamage=(int) (1.1*this.mDamage);
    }
}
