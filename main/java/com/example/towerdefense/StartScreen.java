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

/*
Set up and display the "start" or home screen
 */

public class StartScreen {
    Bitmap background, level1, level2, level3, rules, title, back, soundOn, soundOff; //images
    private int mTextFormatting, mScreenHeight, mScreenWidth, levelButtonWidth, infoButtonWidth;
    private int levelButtonTopPadding, infoButtonPadding, infoButtonHeight;
    private final int levelButtonPadding=20;
    private ArrayList<Rect> buttons; //buttons
    Point size;
    static int LEVEL1 = 0;
    static int LEVEL2 = 1;
    static int LEVEL3=2;
    static int RULES =3;
    static int SOUND =4;


    boolean showingRules;


    static int white = Color.argb(255,255,255,255);
    static int grey = Color.argb(255,161,161,161);

    public StartScreen(Context context, Point size){ //set up start screen


        this.size=size;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        levelButtonWidth=(size.x-80)/3;
        infoButtonWidth = mScreenHeight / 6;
        levelButtonTopPadding=(size.y)/4;
        infoButtonPadding = mScreenWidth / 90;
        infoButtonHeight = mScreenHeight / 12;
        buttons= new ArrayList<Rect>();
        HelperFactory helperFactory = new HelperFactory(context);
        this.level1=helperFactory.bitmapMaker(R.drawable.level1, levelButtonWidth);
        this.level2=helperFactory.bitmapMaker(R.drawable.level2, levelButtonWidth);
        this.level3=helperFactory.bitmapMaker(R.drawable.level3, levelButtonWidth);
        this.rules=helperFactory.bitmapMaker(R.drawable.rules, infoButtonWidth);
        this.back=helperFactory.bitmapMaker(R.drawable.back, infoButtonWidth);
        this.soundOff=helperFactory.bitmapMaker(R.drawable.sound_off, infoButtonWidth);
        this.soundOn=helperFactory.bitmapMaker(R.drawable.sound_on, infoButtonWidth);

        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_page);
        this.title = BitmapFactory.decodeResource(context.getResources(), R.drawable.title);
        title=Bitmap.createScaledBitmap(title, mScreenWidth, levelButtonTopPadding, false);
        makeButtons();
    }
    private void makeButtons(){ //make all the buttons
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
                mScreenWidth-levelButtonPadding-infoButtonHeight*2,
                mScreenHeight-infoButtonPadding-infoButtonHeight*2,
                mScreenWidth-levelButtonPadding,
                mScreenHeight-infoButtonPadding);
        Rect sound = new Rect( //level1
                levelButtonPadding,
                mScreenHeight-infoButtonPadding-infoButtonHeight*2,
                infoButtonHeight*2+levelButtonPadding,
                mScreenHeight-infoButtonPadding);
        buttons.add(LEVEL1, level1);
        buttons.add(LEVEL2, level2);
        buttons.add(LEVEL3, level3);
        buttons.add(RULES, gameInfo);
        buttons.add(SOUND, sound);
    }

    void draw(Canvas canvas, Paint paint, boolean sound){ //display the start screen
        canvas.drawBitmap(background, 0, 0, null);
        if(showingRules){ //if the rules should be shown
            paint.setColor(white); //set color to white
            paint.setTextSize(mTextFormatting);
            canvas.drawText("These are the rules of the game. Don't lose, and make lots of money.", //rules
                    0, buttons.get(RULES).top-(mTextFormatting+2)*2,paint);
            paint.setColor(grey); //set color to black
            canvas.drawRect(buttons.get(RULES).left, buttons.get(RULES).top, buttons.get(RULES).right, buttons.get(RULES).bottom, paint);
            canvas.drawBitmap(back, buttons.get(RULES).left, buttons.get(RULES).top, null);
        }else { //otherwise

            paint.setColor(grey); //set color to black
            for (Rect r : buttons) {
                canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
            }
            canvas.drawBitmap(level1, buttons.get(LEVEL1).left, buttons.get(LEVEL1).top, null);
            canvas.drawBitmap(level2, buttons.get(LEVEL2).left, buttons.get(LEVEL2).top, null);
            canvas.drawBitmap(level3, buttons.get(LEVEL3).left, buttons.get(LEVEL3).top, null);
            canvas.drawBitmap(rules, buttons.get(RULES).left, buttons.get(RULES).top, null);
            canvas.drawBitmap(title, 0, 0, null);
        }
        paint.setColor(grey);
        canvas.drawRect(buttons.get(SOUND).left, buttons.get(SOUND).top, buttons.get(SOUND).right, buttons.get(SOUND).bottom, paint);
        if(sound){ //if the sound is on
            canvas.drawBitmap(soundOn, buttons.get(SOUND).left, buttons.get(SOUND).top, null);
        }else {//otherwise
            canvas.drawBitmap(soundOff, buttons.get(SOUND).left, buttons.get(SOUND).top, null);
        }


    }

    public ArrayList<Rect> getButtons() { //return a list of the buttons
        return buttons;
    }

    public void rulesOnOff(){ //cycle showing the rules
        if(showingRules){
            showingRules=false;
        }else{
            showingRules=true;
        }
    }
}
