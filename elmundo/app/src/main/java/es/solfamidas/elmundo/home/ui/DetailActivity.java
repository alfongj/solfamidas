package es.solfamidas.elmundo.home.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.framework.BaseToolBarActivity;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.presenter.DetailPresenterImpl;

public class DetailActivity extends BaseToolBarActivity implements DetailUI {

    public static final String EXTRA_ARTICLE = "extra_article";

    private ImageView mImageView;
    private TextView mTitleTv, mDescTv, mAuthorTv;

    private DetailPresenterImpl presenter;

    private Article mArticle;

    @Override
    public void injectModuleDependencies() {
        presenter = new DetailPresenterImpl(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageView = (ImageView) findViewById(R.id.articleImageView);
        mTitleTv = (TextView) findViewById(R.id.articleTitleTv);
        mDescTv = (TextView) findViewById(R.id.articleDescTv);
        mAuthorTv = (TextView) findViewById(R.id.articleAuthorTv);

        mArticle = (Article) getIntent().getExtras().getSerializable(EXTRA_ARTICLE);
        presenter.getArticle(mArticle);
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
        mDescTv.setText(mArticle.getDescription());
        mAuthorTv.setText(mArticle.getAuthor());
        Picasso.with(this).load(mArticle.getImage().getThumbnail().getUrl()).into(mImageView);
    }
}
