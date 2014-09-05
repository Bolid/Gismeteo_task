package com.export.Gismeteo_task;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class MyDetectorListener extends GestureDetector.SimpleOnGestureListener {
    String TAG = "GESTURE_DETECTOR";

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i(TAG, "onSingleTapUp");
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        Log.i(TAG, "onScroll");
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        Log.i(TAG, "onFling");
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
