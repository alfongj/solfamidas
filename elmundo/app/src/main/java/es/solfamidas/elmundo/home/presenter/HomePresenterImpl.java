package es.solfamidas.elmundo.home.presenter;

import android.util.Log;

import java.util.List;

import es.solfamidas.elmundo.common.datasource.ElMundoDataSource;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.ui.HomeUi;

/**
 * TODO: Do something
 */
public class HomePresenterImpl implements HomePresenter {

    private static final String TAG = HomePresenterImpl.class.getSimpleName();

    // Injected vars
    HomeUi mUi;
    ElMundoDataSource mElMundoDataSource;

    public HomePresenterImpl(
            HomeUi ui,
            ElMundoDataSource elMundoDataSource
    ) {
        mUi = ui;
        mElMundoDataSource = elMundoDataSource;
    }

    @Override
    public void onResume() {
        mElMundoDataSource.getArticles(
                ElMundoDataSource.Category.ESPANA,
                10,
                0,
                new ElMundoDataSource.ArticlesCallback() {
                    @Override
                    public void onSuccess(List<Article> articleList) {
                        for (Article article : articleList) {
                            Log.e("banana", article.getTitle());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        Log.e(TAG, errorMsg);
                    }
                });
    }

    @Override
    public void loadArticles(
            ElMundoDataSource.Category category,
            int limit,
            int from,
            ElMundoDataSource.ArticlesCallback callback) {
        mElMundoDataSource.getArticles(category, limit, from, callback);
    }
}
