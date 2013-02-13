package dk.dorland.ExpandableToolbar;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

public class ExpandAnimation extends Animation {
    protected View mAnimatedView;
    protected LayoutParams mLayoutParams;
    protected int mMarginStart, mMarginEnd;
    protected boolean mIsVisible = false;

    public ExpandAnimation(View view, int duration) {

        setDuration(duration);
        mAnimatedView = view;
        mLayoutParams = (LayoutParams) view.getLayoutParams();

        // Decide to show or hide the view
        mIsVisible = (view.getVisibility() == View.VISIBLE);

        mMarginStart = mLayoutParams.bottomMargin;
        mMarginEnd = (mMarginStart == 0 ? (0- view.getHeight()) : 0);

        view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        if (interpolatedTime < 1.0f) {
            // Setting the new border margin
            mLayoutParams.bottomMargin = mMarginStart
                    + (int) ((mMarginEnd - mMarginStart) * interpolatedTime);

            // Invalidating the layout, forcing redraw
            mAnimatedView.requestLayout();

        } else {
            mLayoutParams.bottomMargin = mMarginEnd;
            mAnimatedView.requestLayout();

            if (mIsVisible) {
                mAnimatedView.setVisibility(View.GONE);
            }
        }
    }
}