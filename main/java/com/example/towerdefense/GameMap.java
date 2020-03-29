package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

public class GameMap
{
    Bitmap mMap, base, path;
    Point size;

    //GameMap constructor
    public GameMap(Context context, Point size)
    {
        this.mMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        this.base = BitmapFactory.decodeResource(context.getResources(), R.drawable.base);
        this.path = BitmapFactory.decodeResource(context.getResources(), R.drawable.path);
        this.path = Bitmap.createScaledBitmap(path, size.x, 100, true);
        this.mMap = Bitmap.createScaledBitmap(mMap, size.x, size.y, true);
        this.size = size;
    }

    //Draw the map
    void draw (Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mMap, 0, 0, null);
        canvas.drawBitmap(path, 0, (size.y / 2) - (path.getHeight() / 2), null);
        canvas.drawBitmap(base, size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2), null);
    }

    //Returns the hitbox of the base. Used to determine if the aliens intersect.
    public Rect getBaseCords()
    {
        return new Rect(size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2), size.x, (size.y / 2) + (base.getHeight() / 2));
    }

    //Used when an alien spawns to verify they spawn on the path.
    public int getPathHeight()
    {
        return path.getHeight();
    }

    //Spawns a pre-defined set of waves when called.
    public ArrayList<Alien> spawn(Context context, int wave)
    {
        ArrayList<Alien> newAliens = new ArrayList<>();

        switch(wave)
        {
            case 1:
                for (int count = 0; count < 5; count++)
                {
                    newAliens.add(new AlienEnemy(context, size, getPathHeight(), "drone").getAlien());
                }
                break;

            case 2:
                for (int count = 0; count < 10; count++)
                {
                    newAliens.add(new AlienEnemy(context, size, getPathHeight(), "drone").getAlien());
                }
                break;

            case 3:
                for (int count = 0; count < 15; count++)
                {
                    newAliens.add(new AlienEnemy(context, size, getPathHeight(), "drone").getAlien());
                }
                break;
        }
        return newAliens;
    }
}
