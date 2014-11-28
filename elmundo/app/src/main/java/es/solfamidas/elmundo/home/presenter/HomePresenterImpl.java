package es.solfamidas.elmundo.home.presenter;

import android.util.Log;

import java.util.List;

import es.solfamidas.elmundo.common.datasource.ElMundoDataSource;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.main.ui.MainUi;

/**
 * TODO: Do something
 */
public class HomePresenterImpl implements HomePresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    // Injected vars
    MainUi mUi;
    ElMundoDataSource mElMundoDataSource;

    public HomePresenterImpl(
            MainUi ui,
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
}
