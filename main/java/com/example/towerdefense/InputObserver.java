package com.example.towerdefense;

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public interface InputObserver
{
    void handleInput(MotionEvent event, GameWorld gs, ArrayList<Rect> controls);
}
