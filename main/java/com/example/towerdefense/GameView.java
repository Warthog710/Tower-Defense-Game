package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;

class GameView
{
    private Context context;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Point size;

    GameView(SurfaceView surfaceView, Context context, Point size)
    {
        this.context = context;
        mSurfaceHolder = surfaceView.getHolder();
        mPaint = new Paint();
        this.size = size;
    }

    void draw(GameWorld gameWorld, HUD mHUD, long FPS)
    {
        if(mSurfaceHolder.getSurface().isValid())
        {
            mCanvas = mSurfaceHolder.lockCanvas();

            //Draw game background
            gameWorld.mMap.draw(mCanvas, mPaint);

            //If currently drawing...
            if (gameWorld.getDrawing())
            {
                //Draw projectiles
                if (gameWorld.mProjectiles != null)
                {
                    Iterator<Projectile> projectileIterator = gameWorld.mProjectiles.iterator();
                    while(projectileIterator.hasNext())
                    {
                        projectileIterator.next().draw(mCanvas,mPaint);
                    }
                }

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

                //draw the range of a turret
                if (gameWorld.range != null){
                    gameWorld.range.draw(mCanvas, mPaint);
                }
            }

            //Draw HUD
            mHUD.draw(mCanvas, mPaint, gameWorld, FPS);

            //Unlock and post
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
