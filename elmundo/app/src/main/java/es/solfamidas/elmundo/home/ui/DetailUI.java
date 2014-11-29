package es.solfamidas.elmundo.home.ui;

import es.solfamidas.elmundo.entities.Article;

public interface DetailUI {

    public void showErrorMsg(String error);

    //TODO pass Article through presenter
    public void renderArticle(Article mArticle);

}
