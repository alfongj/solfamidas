package es.solfamidas.elmundo.home.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.datasource.ElMundoDataSource;
import es.solfamidas.elmundo.common.viewmodel.ArticleCard;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.presenter.HomePresenter;
import es.solfamidas.elmundo.home.ui.ArticleCardClickListener;
import es.solfamidas.elmundo.home.ui.DetailActivity;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.dismiss.DefaultDismissableManager;

/**
 * Contains a feed of cards of a given category.
 *
 * TODO Load more articles when scrolling down.
 */
public class ArticleFeedFragment extends Fragment implements ArticleCardClickListener{

    private static final String FEED_TYPE_ARG = "FEED_TYPE";

    private ElMundoDataSource.Category mCategory;
    private HomePresenter mHomePresenter;
    private ContentLoadingProgressBar mProgressBar;

    // Views
    private View mRootView;


    public void setPresenter(HomePresenter homePresenter) {
        mHomePresenter = homePresenter;
    }

    public static ArticleFeedFragment newInstance(
            ElMundoDataSource.Category feedTypeByTabIndex) {
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

        mRootView = inflater.inflate(R.layout.fragment_article_feed, container, false);
        mProgressBar = (ContentLoadingProgressBar) mRootView.findViewById(R.id.progress_bar);
        mProgressBar.show();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadArticles();
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
        fillArticleCardList(articleList);
    }

    private void fillArticleCardList(List<Article> articles) {

        ArrayList<Card> cards = new ArrayList<Card>();

        for (Article article : articles) {
            ArticleCard card = new ArticleCard(getActivity(), R.layout.card_layout);
            card.setSwipeable(true);
            card.setArticleContent(article);
            card.setListener(this);
            card.setCardElevation(5);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        mCardArrayAdapter.setDismissable(new DefaultDismissableManager());
        mCardArrayAdapter.setEnableUndo(true);

        CardListView listView = (CardListView) mRootView.findViewById(R.id.article_list);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    @Override
    public void onArticleClick(Article article) {
        Intent detailsIntent = new Intent(getActivity(), DetailActivity.class);
        detailsIntent.putExtra(DetailActivity.EXTRA_ARTICLE, article);
        startActivity(detailsIntent);
    }
}
