package es.solfamidas.elmundo.home.ui;

/**
 * Created by carlos on 29/11/14.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

import es.solfamidas.elmundo.R;

public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    private boolean onTop = true;
    private float mHeaderHeight = 0;

    public ObservableScrollView(Context context) {
        super(context);
        mHeaderHeight = context.getResources().getDimension(R.dimen.activity_detail_image_height);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHeaderHeight = context.getResources().getDimension(R.dimen.activity_detail_image_height);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeaderHeight = context.getResources().getDimension(R.dimen.activity_detail_image_height);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if (scrollViewListener != null) {

            if (y <= mHeaderHeight && !onTop) {
                scrollViewListener.onReachTop();
                onTop = true;
            } else if (y > mHeaderHeight && onTop) {
                scrollViewListener.onLeaveTop();
                onTop = false;
            }
        }
    }

}
