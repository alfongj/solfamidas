package es.solfamidas.elmundo.common.viewmodel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.solfamidas.elmundo.R;
import it.gmariotti.cardslib.library.internal.Card;

public class ArticleCard extends Card {

    protected TextView mTitle;
    protected TextView mDescription;
    protected ImageView mImage;

    public String title, description, imgUrl;

    /**
     * Constructor with a custom inner layout
     *
     * @param context
     */
    public ArticleCard(Context context) {
        this(context, R.layout.card_layout);
    }

    /**
     * @param context
     * @param innerLayout
     */
    public ArticleCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    public void setArticleContent(String title, String desc, String imgUrl) {
        this.title = title;
        this.description = desc;
        this.imgUrl = imgUrl;
    }

    /**
     * Init
     */
    private void init() {

        //No Header

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        parent.setPadding(0, 0, 0, 0);

        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.card_article_title);
        mDescription = (TextView) parent.findViewById(R.id.card_article_desc);
        mImage = (ImageView) parent.findViewById(R.id.card_article_image);

        if (mTitle != null) {
            mTitle.setText(title);
        }
        if (mDescription != null) {
            mDescription.setText(description);
        }
        if (mImage != null) {
            Picasso.with(getContext()).load(imgUrl).resize(800, 500).centerCrop().into(mImage);
        }
    }
}

