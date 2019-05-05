package br.com.aleson.nasa.apod.app.feature.apod.presentation.adapter;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;

import br.com.aleson.nasa.apod.app.common.constants.Constants;

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

        if (!onScrolling) {
            try {
                if (e1.getX() < e2.getX()) {

                    return onSwipeRight();
                }

                if (e1.getX() > e2.getX()) {

                    return onSwipeLeft();
                }
            } catch (Exception e) {

                SLogger.e(e);
            }
        }
        return false;
    }

    private boolean onScrolling = false;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if (!onScrolling) {
            try {
                if (e1.getX() < e2.getX()) {

                    onScrolling = true;
                    coolDownScrolling();
                    return onSwipeRight();
                }

                if (e1.getX() > e2.getX()) {

                    onScrolling = true;
                    coolDownScrolling();
                    return onSwipeLeft();
                }
            } catch (Exception e) {

                SLogger.e(e);
                onScrolling = false;
            }
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public abstract boolean onSwipeRight();

    public abstract boolean onSwipeLeft();

    public abstract boolean onTouch();

    public void coolDownScrolling() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScrolling = false;
            }
        }, Constants.TIME_LAPSE.DEFAULT);
    }

}
