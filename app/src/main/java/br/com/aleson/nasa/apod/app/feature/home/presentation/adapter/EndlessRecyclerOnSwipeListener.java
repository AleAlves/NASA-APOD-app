package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.view.MotionEvent;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.aleson.nasa.apod.app.common.Constants;

public abstract class EndlessRecyclerOnSwipeListener extends RecyclerView.OnScrollListener {

    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private static boolean DRAGING;
    private boolean mLoading;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 0;
        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            onGetApod(verifySwipeDirection(dx));

            mLoading = true;
        }


    }

    private int verifySwipeDirection(int axisX) {
        if (axisX > 0) {
            return Constants.Swipeirection.RIGHT;
        } else if (axisX <= 0) {
            return Constants.Swipeirection.LEFT;
        } else {
            return 0;
        }
    }


    public abstract void onGetApod(int XAxis);

}
