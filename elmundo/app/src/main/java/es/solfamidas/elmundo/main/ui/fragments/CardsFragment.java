package es.solfamidas.elmundo.main.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.viewmodel.ArticleCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.dismiss.DefaultDismissableManager;

public class CardsFragment extends Fragment {

    View mRootView;

    public CardsFragment() {
    }

    public void fillArticleCardList(List<T> articles) {

        ArrayList<Card> cards = new ArrayList<Card>();

        for (T article : articles) {
            //Create a Card
            ArticleCard card = new ArticleCard(getActivity(), R.layout.card_layout);
            card.setSwipeable(true);
            card.setId("" + guid);
            card.setArticleContent("Cristiano Ronaldo sin camiseta ", "No ase falta desir nada mas", imageUrls.get(i));
            card.setCardElevation(100);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_card, container, false);
        }
        return mRootView;

    }
}

