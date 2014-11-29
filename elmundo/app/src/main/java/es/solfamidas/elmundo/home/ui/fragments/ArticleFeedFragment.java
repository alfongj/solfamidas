package es.solfamidas.elmundo.home.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.datasource.ElMundoDataSource;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.presenter.HomePresenter;

/**
 * Contains a feed of cards of a given category.
 *
 * TODO Load more articles when scrolling down.
 */
public class ArticleFeedFragment extends Fragment {

    private static final String FEED_TYPE_ARG = "FEED_TYPE";

    private ElMundoDataSource.Category mCategory;
    private HomePresenter mHomePresenter;
    private ContentLoadingProgressBar mProgressBar;

    // Views
    private TextView dummy;



    public void setPresenter(HomePresenter homePresenter) {
        mHomePresenter = homePresenter;
    }

    public static ArticleFeedFragment newInstance(ElMundoDataSource.Category feedTypeByTabIndex) {
        ArticleFeedFragment fragment = new ArticleFeedFragment();
        Bundle args = new Bundle();
        args.putSerializable(FEED_TYPE_ARG, feedTypeByTabIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategory = (ElMundoDataSource.Category) getArguments().getSerializable(FEED_TYPE_ARG);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);

        View root = inflater.inflate(R.layout.fragment_article_feed, container, false);
        mProgressBar = (ContentLoadingProgressBar) root.findViewById(R.id.progress_bar);
        mProgressBar.show();
        dummy = (TextView) root.findViewById(R.id.dummy);
        loadArticles();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        dummy.setText(mCategory.toString());
        dummy.setVisibility(View.VISIBLE);
    }

    private void loadArticles() {
        mHomePresenter.loadArticles(mCategory, 5, 0, new ElMundoDataSource.ArticlesCallback() {
            @Override
            public void onSuccess(List<Article> articleList) {
                onArticlesLoaded(articleList);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onArticlesLoaded(List<Article> articleList) {
        StringBuilder sb = new StringBuilder();
        for (Article article : articleList) {
            sb.append(article.getTitle() + "\n");
        }
        dummy.setText(sb.toString());
    }
}
