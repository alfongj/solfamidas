package es.solfamidas.elmundo.main.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.viewmodel.ArticleCard;
import es.solfamidas.elmundo.entities.Article;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.dismiss.DefaultDismissableManager;

public class CardsFragment extends Fragment {

    View mRootView;

    public CardsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_card, container, false);

            //TODO pass Article list
            //fillArticleCardList(articles);
        }
        return mRootView;

    }

    public void fillArticleCardList(List<Article> articles) {

        ArrayList<Card> cards = new ArrayList<Card>();

        for (Article article : articles) {
            ArticleCard card = new ArticleCard(getActivity(), R.layout.card_layout);
            card.setSwipeable(true);
            card.setId(article.getGuid());
            card.setArticleContent(article.getTitle(), article.getDescription(), article.getImage().getThumbnail().getUrl());
            card.setCardElevation(5);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        mCardArrayAdapter.setDismissable(new DefaultDismissableManager());
        mCardArrayAdapter.setEnableUndo(true);

        CardListView listView = (CardListView) mRootView.findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }

}

