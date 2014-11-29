package es.solfamidas.elmundo.home.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.framework.BaseToolBarActivity;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.presenter.DetailPresenterImpl;

public class DetailActivity extends BaseToolBarActivity implements DetailUI, ScrollViewListener {

    public static final String EXTRA_ARTICLE = "extra_article";
    public static final String NO_IMAGE_URL = "http://www.lucirfashion.com/blog/images/img_not_found.gif";

    private ImageView mImageView;
    private TextView mTitleTv, mDescTv, mAuthorTv, mDateTv;
    private ObservableScrollView mScrollView;

    private DetailPresenterImpl presenter;

    private Article mArticle;

    private Drawable oldBackground = null;

    private final Handler handler = new Handler();


    @Override
    public void injectModuleDependencies() {
        presenter = new DetailPresenterImpl(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));

        mImageView = (ImageView) findViewById(R.id.articleImageView);
        mTitleTv = (TextView) findViewById(R.id.articleTitleTv);
        mDescTv = (TextView) findViewById(R.id.articleDescTv);
        mAuthorTv = (TextView) findViewById(R.id.articleAuthorTv);
        mDateTv = (TextView) findViewById(R.id.articleDateTv);

        mScrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        mScrollView.setScrollViewListener(this);

        mArticle = (Article) getIntent().getExtras().getSerializable(EXTRA_ARTICLE);
        presenter.getArticle(mArticle);

        setActionBarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share) {
            presenter.shareArticle(mArticle);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this, "Error loading Article", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void renderArticle(Article mArticle) {
        mTitleTv.setText(mArticle.getTitle());
        mDescTv.setText(Html.fromHtml(mArticle.getDescription()));
        mDescTv.setMovementMethod(LinkMovementMethod.getInstance());
        mAuthorTv.setText(mArticle.getAuthor());
        mDateTv.setText("Actualizado: " + mArticle.getDate());

        if (mImageView != null) {
            String thumbnailUrl = "";
            if (mArticle.getImage() != null) {
                thumbnailUrl = mArticle.getImage().getUrl();
                Picasso.with(this).load(thumbnailUrl).into(mImageView);
            } else {
                Picasso.with(this).load(NO_IMAGE_URL).into(mImageView);
            }
        }
    }

    @Override
    public void onReachTop() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
    }

    @Override
    public void onLeaveTop() {
        changeColor(0xFF33393B);

    }

    private void changeColor(int newColor) {

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

                TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});

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
}
