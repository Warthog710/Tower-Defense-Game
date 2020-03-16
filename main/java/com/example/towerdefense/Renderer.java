package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Renderer
{
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    Renderer(SurfaceView surfaceView)
    {
        mSurfaceHolder = surfaceView.getHolder();
        mPaint = new Paint();
    }

    void draw(GameState gamestate)
    {
        if(mSurfaceHolder.getSurface().isValid())
        {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 0, 0, 0));

            //Test code to verify the program is drawing... DELETE ME!!!
            mPaint.setTextSize(100);
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mCanvas.drawText("This is a test!!!", 100, 500, mPaint);

            if (gamestate.getDrawing())
            {
                //Draw all game objects...
            }

            if(gamestate.getGameOver())
            {
                //Draw game over screen...
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
