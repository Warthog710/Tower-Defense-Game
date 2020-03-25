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
            //Note this should change to use a Map object...
            Bitmap background = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
            mCanvas.drawBitmap(background, 0, 0, null);



            if (gameWorld.getDrawing())
            {

                //draw towers
                if (gameWorld.mTowers != null){
                    //System.out.println("Drawing tower");
                    Iterator<Tower> towerIterator = gameWorld.mTowers.iterator();
                    while(towerIterator.hasNext()){
                        towerIterator.next().draw(mCanvas,mPaint);
                    }

                }

                //draw aliens, currently not working
                if (gameWorld.mAliens != null){ //update later
                    //System.out.println("Drawing alien");
                    Iterator<AlienEnemy> alienIterator = gameWorld.mAliens.iterator();
                    while(alienIterator.hasNext()){
                        (alienIterator.next()).alien.draw(mCanvas,mPaint);
                    }

                }
                //draw towers
                if (gameWorld.mProjectiles != null){
                    //System.out.println("Drawing tower");
                    Iterator<Projectile> projectileMoveableIterator = gameWorld.mProjectiles.iterator();
                    while(projectileMoveableIterator.hasNext()){
                        projectileMoveableIterator.next().draw(mCanvas,mPaint);
                    }

                }
            }

            if(gameWorld.getGameOver())
            {
                //Draw game over screen...
            }

            //draw HUD

            mHUD.draw(mCanvas, mPaint, gameWorld);

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
