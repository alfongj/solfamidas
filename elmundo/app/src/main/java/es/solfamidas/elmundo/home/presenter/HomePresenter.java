package es.solfamidas.elmundo.home.presenter;

import es.solfamidas.elmundo.common.datasource.ElMundoDataSource;

/**
 * TODO: Add methods that orchestrate the logic in the Main module
 */
public interface HomePresenter {

    public void onResume();

    public void loadArticles(
            ElMundoDataSource.Category category,
            int limit,
            int from,
            ElMundoDataSource.ArticlesCallback callback);
}
