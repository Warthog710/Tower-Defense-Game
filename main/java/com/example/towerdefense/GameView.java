package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    void draw(GameWorld gameWorld)
    {
        if(mSurfaceHolder.getSurface().isValid())
        {
            mCanvas = mSurfaceHolder.lockCanvas();

            //Draw game background
            //Note this should change to use a Map object...
            Bitmap background = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
            mCanvas.drawBitmap(background, 0, 0, null);

            //Test draw
            gameWorld.enemy.alien.draw(mCanvas, mPaint);


            if (gameWorld.getDrawing())
            {
                //Draw all game objects...
            }

            if(gameWorld.getGameOver())
            {
                //Draw game over screen...
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
