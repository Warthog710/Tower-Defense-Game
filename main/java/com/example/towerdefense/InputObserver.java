package com.example.towerdefense;

import android.view.MotionEvent;

public interface InputObserver
{
    void handleInput(MotionEvent event, GameWorld gameState, HUD mHud);
}
