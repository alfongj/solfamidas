package es.solfamidas.elmundo.home.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.astuetz.PagerSlidingTabStrip;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.datasource.ElMundoDataSourceImpl;
import es.solfamidas.elmundo.common.framework.BaseToolBarActivity;
import es.solfamidas.elmundo.home.presenter.HomePresenter;
import es.solfamidas.elmundo.home.presenter.HomePresenterImpl;

public class HomeActivity
        extends BaseToolBarActivity
        implements HomeUi {

    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ArticleFeedPagerAdapter adapter;
    private int currentColor = 0xFF406599;

    private Drawable oldBackground = null;

    // Injected vars
    private HomePresenter mPresenter;

    @Override
    public void injectModuleDependencies() {
        mPresenter = new HomePresenterImpl(
                this,
                new ElMundoDataSourceImpl(this)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolBar();

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new ArticleFeedPagerAdapter(
                getSupportFragmentManager(),
                mPresenter);

        final int pageMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                changeColor(getColorForTab(i));
            }
        });

        changeColor(currentColor);

    }

    private int getColorForTab(int i) {
        switch (i) {
            case 0:
                return 0xFF406599;
            case 1:
                return 0xFFB8CFE1;
            case 2:
                return 0xFF908D5D;
            case 3:
                return 0xFFF2AA52;
            case 4:
                return 0xFF33393B;
            default:
                return Color.BLACK;
        }
    }

    private void setupToolBar() {
        setActionBarIcon(R.drawable.ic_ab_drawer);
        setActionBarTitle("El mundo");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getSupportActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (oldBackground == null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    getSupportActionBar().setBackgroundDrawable(ld);
                }
            } else {
                TransitionDrawable td = new TransitionDrawable(new Drawable[] {oldBackground, ld});

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    getSupportActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);
            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        currentColor = newColor;
    }

}
