package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class StartScreen {
    Bitmap background, level1, level2, level3;
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<Rect> buttons;
    Point size;
    static int LEVEL1 = 0;
    static int LEVEL2 = 1;
    static int LEVEL3=2;
    static int GAMEINFO=3;


    static int white = Color.argb(255,255,255,255);
    static int grey = Color.argb(255,161,161,161);

    public StartScreen(Context context, Point size){


        this.size=size;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        int levelButtonWidth=(size.x-80)/3;
        buttons= new ArrayList<Rect>();
        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_page);
        this.level1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.level1);
        level1=Bitmap.createScaledBitmap(level1, levelButtonWidth, levelButtonWidth, false);
        this.level2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.level2);
        level2=Bitmap.createScaledBitmap(level2, levelButtonWidth, levelButtonWidth, false);
        this.level3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.level3);
        level3=Bitmap.createScaledBitmap(level3, levelButtonWidth, levelButtonWidth, false);
        makeButtons();
    }
    private void makeButtons(){
        int levelButtonWidth=(size.x-80)/3;
        int levelButtonPadding=20;
        int levelButtonTopPadding=(size.y)/4;
        int infoButtonPadding = mScreenWidth / 90;
        int infoButtonWidth = mScreenWidth / 14;
        int infoButtonHeight = mScreenHeight / 12;
        Rect level1 = new Rect( //level1
                levelButtonPadding,
                levelButtonTopPadding,
                levelButtonPadding+levelButtonWidth,
                levelButtonTopPadding+levelButtonWidth);
        Rect level2 = new Rect( //level1
                levelButtonPadding*2+levelButtonWidth,
                levelButtonTopPadding,
                levelButtonPadding*2+levelButtonWidth*2,
                levelButtonTopPadding+levelButtonWidth);
        Rect level3 = new Rect( //level1
                levelButtonPadding*3+levelButtonWidth*2,
                levelButtonTopPadding,
                levelButtonPadding*2+levelButtonWidth*3,
                levelButtonTopPadding+levelButtonWidth);
        Rect gameInfo = new Rect( //level1
                mScreenWidth-infoButtonPadding-infoButtonWidth*2,
                mScreenHeight-infoButtonPadding-infoButtonHeight*2,
                mScreenWidth-infoButtonPadding,
                mScreenHeight-infoButtonPadding);
        buttons.add(LEVEL1, level1);
        buttons.add(LEVEL2, level2);
        buttons.add(LEVEL3, level3);
        buttons.add(GAMEINFO, gameInfo);
    }

    void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(background, 0, 0, null);

        paint.setColor(grey); //set color to black
        for(Rect r : buttons)
        {
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }
        canvas.drawBitmap(level1, buttons.get(LEVEL1).left,  buttons.get(LEVEL1).top, null);
        canvas.drawBitmap(level2, buttons.get(LEVEL2).left,  buttons.get(LEVEL2).top, null);
        canvas.drawBitmap(level3, buttons.get(LEVEL3).left,  buttons.get(LEVEL3).top, null);

    }
}
