package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class APODGestureListener extends GestureDetector.SimpleOnGestureListener {

    private final View mView;

    public APODGestureListener(View view) {
        mView = view;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mView.onTouchEvent(e);
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        onTouch();
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() < e2.getX()) {
            return onSwipeRight();
        }

        if (e1.getX() > e2.getX()) {
            return onSwipeLeft();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public abstract boolean onSwipeRight();

    public abstract boolean onSwipeLeft();

    public abstract boolean onTouch();

}
