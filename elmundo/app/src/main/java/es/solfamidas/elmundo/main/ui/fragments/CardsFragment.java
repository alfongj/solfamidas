package es.solfamidas.elmundo.main.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.datasource.FlickrDataSource;
import es.solfamidas.elmundo.common.datasource.FlickrDataSourceImpl;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardViewNative;

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

            ArrayList<Card> cards = new ArrayList<Card>();

            for (int i = 0; i<10; i++){

                //Create a Card
                Card card = new Card(getActivity());

                //Create thumbnail
                CardThumbnail thumb = new CardThumbnail(getActivity());
                thumb.setDrawableResource(R.drawable.ic_launcher);

                //Create header
                CardHeader header = new CardHeader(getActivity());
                header.setTitle("Titulo del card");

                //Add content to card
                card.addCardThumbnail(thumb);
                card.addCardHeader(header);
                card.setTitle("hola, soy descripcion");

                cards.add(card);
            }

            CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

            CardListView listView = (CardListView) mRootView.findViewById(R.id.myList);
            if (listView!=null){
                listView.setAdapter(mCardArrayAdapter);
            }
        }
        return mRootView;
    }
}
