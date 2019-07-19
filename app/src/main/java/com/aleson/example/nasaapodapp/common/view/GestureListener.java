package com.aleson.example.nasaapodapp.common.view;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;

import com.aleson.example.nasaapodapp.common.constants.Constants;

public abstract class GestureListener extends GestureDetector.SimpleOnGestureListener implements
        View.OnTouchListener, GestureDetector.OnGestureListener {

    private final GestureDetector gestureScanner;

    public GestureListener(Context context) {
        gestureScanner = new GestureDetector(context, this);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return super.onDoubleTapEvent(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        onTouch();
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        try {
            if (!onScrolling) {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > 200 && Math.abs(velocityX) > 200) {
                        if (diffX > 0) {
                            coolDownScrolling();
                            return onSwipeRight();
                        } else {
                            coolDownScrolling();
                            return onSwipeLeft();
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean onScrolling = false;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


        try {
            if (!onScrolling) {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > 200 && Math.abs(1.0) > 200) {
                        if (diffX > 0) {
                            coolDownScrolling();
                            return onSwipeRight();
                        } else {
                            coolDownScrolling();
                            return onSwipeLeft();
                        }
                    }
                }
            }
        } catch (Exception e) {

            SLogger.e(e);
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public abstract boolean onSwipeRight();

    public abstract boolean onSwipeLeft();

    public abstract boolean onTouch();

    public void coolDownScrolling() {

        onScrolling = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScrolling = false;
                Log.w("YK308", "onScrolling false");
            }
        }, Constants.TIME_LAPSE.DEFAULT);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureScanner.onTouchEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return gestureScanner.onTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent me) {
        return gestureScanner.onTouchEvent(me);
    }
}
