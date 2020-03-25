package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Projectile extends GameObject {
    int mDamage;
    int mSpeed;
    public static final int SIZE=10;
    public Projectile(){

    }
    public Projectile(int mDamage, int mSpeed, Context context){
        this.mDamage=mDamage;
        this.mSpeed=mSpeed;
        this.mBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.test_bullet);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, SIZE, SIZE, false);
    }

}
