package es.solfamidas.elmundo.home.presenter;

import es.solfamidas.elmundo.entities.Article;

/**
 * Created by carlos on 29/11/14.
 */
public interface DetailPresenter {

    public void getArticle(Article mArticle);
    public void shareArticle(Article mArticle);

}
