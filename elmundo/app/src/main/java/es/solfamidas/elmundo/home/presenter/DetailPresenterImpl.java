package es.solfamidas.elmundo.home.presenter;

import android.content.Context;
import android.content.Intent;

import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.ui.DetailUI;

public class DetailPresenterImpl implements DetailPresenter {

    // Injected vars
    private final DetailUI mUi;
    private final Context mContext;

    public DetailPresenterImpl(
            DetailUI ui, Context context
    ) {
        mUi = ui;
        mContext = context;
    }

    @Override
    public void getArticle(Article mArticle) {
        if(mArticle != null){
            mUi.renderArticle(mArticle);
        }else{
            mUi.showErrorMsg("Error rendering article");
        }
    }

    @Override
    public void shareArticle(Article mArticle) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mArticle.toString());
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, "Compartir por..."));
    }
}
