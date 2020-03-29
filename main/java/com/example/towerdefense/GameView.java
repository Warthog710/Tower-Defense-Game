package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;

class GameView
{
    private Context context;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    GameView(SurfaceView surfaceView, Context context)
    {
        this.context = context;
        mSurfaceHolder = surfaceView.getHolder();
        mPaint = new Paint();
    }

    void draw(GameWorld gameWorld, HUD mHUD)
    {
        if(mSurfaceHolder.getSurface().isValid())
        {
            mCanvas = mSurfaceHolder.lockCanvas();

            //Draw game background
            gameWorld.mMap.draw(mCanvas, mPaint);

            //If currently drawing...
            if (gameWorld.getDrawing())
            {

                //Draw towers
                if (gameWorld.mTowers != null)
                {
                    Iterator<Tower> towerIterator = gameWorld.mTowers.iterator();
                    while(towerIterator.hasNext())
                    {
                        towerIterator.next().draw(mCanvas,mPaint);
                    }

                }

                //Draw aliens
                if (gameWorld.mAliens != null)
                {
                    Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
                    while(alienIterator.hasNext())
                    {
                        (alienIterator.next()).draw(mCanvas,mPaint);
                    }
                }

                //Draw projectiles
                if (gameWorld.mProjectiles != null)
                {
                    Iterator<Projectile> projectileMoveableIterator = gameWorld.mProjectiles.iterator();
                    while(projectileMoveableIterator.hasNext())
                    {
                        projectileMoveableIterator.next().draw(mCanvas,mPaint);
                    }
                }
            }

            //If the game is over...
            if(gameWorld.getGameOver())
            {
                //Draw game over screen...
            }

            //Draw HUD
            mHUD.draw(mCanvas, mPaint, gameWorld);

            //Unlock and post
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
