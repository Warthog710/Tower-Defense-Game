package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
Helper factory for making objects such as bitmaps
 */

public class HelperFactory {
    Context context;

    public HelperFactory(Context context){
        this.context=context;

    }

    public Bitmap bitmapMaker(int id, int size){ //make a square bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),id);
        bitmap=Bitmap.createScaledBitmap(bitmap, size, size, false);
        return bitmap;
    }
    public Bitmap bitmapMaker(int id, int width, int height){ //make a rectangular bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),id);
        bitmap=Bitmap.createScaledBitmap(bitmap, width, height, false);
        return bitmap;
    }
}
