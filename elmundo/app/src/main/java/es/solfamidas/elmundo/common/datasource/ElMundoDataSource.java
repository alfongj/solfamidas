package es.solfamidas.elmundo.common.datasource;

import java.util.List;

import es.solfamidas.elmundo.entities.Article;

/**
 * Fetches info from El Mundo.
 */
public interface ElMundoDataSource {

    public enum Category {
        CIENCIA,
        ESPANA,
        ECONOMIA,
        INTERNACIONAL,
        MI_MUNDO
    }

    public void getArticles(Category cat, int limit, int from, ArticlesCallback callback);

    public void getArticles(Category cat, int limit, ArticlesCallback callback);

    public interface ArticlesCallback {

        public void onSuccess(List<Article> articleList);

        public void onError(String errorMsg);
    }
}
